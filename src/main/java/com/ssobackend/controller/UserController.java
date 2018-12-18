package com.ssobackend.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssobackend.config.security.RequestContext;
import com.ssobackend.domain.dto.CreateUserDTO;
import com.ssobackend.domain.dto.PermissionDTO;
import com.ssobackend.domain.dto.RoleDTO;
import com.ssobackend.domain.dto.UpdateUserDTO;
import com.ssobackend.domain.dto.UserListItemDTO;
import com.ssobackend.domain.model.Permission;
import com.ssobackend.domain.model.Role;
import com.ssobackend.domain.model.User;
import com.ssobackend.domain.service.RoleService;
import com.ssobackend.domain.service.UserDetailsServiceImpl;
import com.ssobackend.exception.InvalidPasswordStrengthException;
import com.ssobackend.util.ApiJsonResponse;
import com.ssobackend.util.ApiMessageLookup;

@RestController
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserDetailsServiceImpl userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ApiMessageLookup messageLookup;
	
	//=========================== User Management =========================//
	@PreAuthorize("hasAuthority('VIEW_ALL_USERS')")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<UserListItemDTO>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		List<UserListItemDTO> allUsersDtoList = new ArrayList<>();
		
		users.forEach(user -> {
			UserListItemDTO listItem = new UserListItemDTO();
			listItem.setEnabled(user.isEnabled());
			listItem.setId(user.getId());
			listItem.setUsername(user.getUsername());
			
			allUsersDtoList.add(listItem);
		});
		
		return new ResponseEntity<List<UserListItemDTO>>(allUsersDtoList, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('CREATE_A_USER')")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<ApiJsonResponse> createUser(@Valid @RequestBody CreateUserDTO newUser){
		if (userService.userExists(newUser.getUsername())) {
			ApiJsonResponse response = new ApiJsonResponse(false, messageLookup.getMessage("user.createUser.duplicateUser"));
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		if(userService.userExists(newUser.getEmployeeId())){
			ApiJsonResponse response = new ApiJsonResponse(false, "A User for the specified Employee already exists.");
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		User user = new User();
		user.setUsername(newUser.getUsername());
		
		user = userService.createUser(user);
		
		ApiJsonResponse response = new ApiJsonResponse(true, messageLookup.getMessage("user.createUser.UserCreated"));
		return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('UPDATE_A_USER')")
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<ApiJsonResponse> updateUser(@RequestBody UpdateUserDTO updateUserDto) {
		User user = userService.getUserByUserId(updateUserDto.getId());
		ApiJsonResponse response;
		if(user == null){
			response = new ApiJsonResponse(false, messageLookup.getMessage("user.updateUser.invalidUserId"));
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.OK);
		}
		List<Role> updatedRoles = new ArrayList<>();
		user.getAuthorities().clear();
		updateUserDto.getRoles().forEach(roleDtoItem ->{
			Role role = roleService.findRoleById(roleDtoItem.getId());
			if(role != null){
				updatedRoles.add(role);
			}
		});
		
		user.setAuthorities(updatedRoles);
		user.setEnabled(updateUserDto.isEnabled());
		user.setCredentialsNonExpired(!updateUserDto.isCredentialsExpired());
		user.setAccountNonLocked(!updateUserDto.isAccountLocked());
		
		userService.updateUser(user);
		
		response = new ApiJsonResponse(true, messageLookup.getMessage("user.updateUser.userUpdated"));
		return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<ApiJsonResponse> changePassword(@RequestBody Map<String, Object> model,
			Principal principal) throws InvalidPasswordStrengthException {
		String newPassword = null;
		if (model.containsKey("newPassword")) {
			newPassword = model.get("newPassword").toString();
		}
		
		if (StringUtils.isBlank(newPassword)) {
			ApiJsonResponse response = new ApiJsonResponse(false, messageLookup.getMessage("password.changePasswrod.emptyNewPassword"));
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		String oldPassword = userService.findByUsername(principal.getName()).getPassword();

		if (newPassword.toString().equals(oldPassword.toString())) {
			ApiJsonResponse response = new ApiJsonResponse(false,
					messageLookup.getMessage("password.changePassword.differentNewPassword"));
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		userService.changePassword(newPassword, RequestContext.getCurrentUser().getId());

		ApiJsonResponse response = new ApiJsonResponse(true, messageLookup.getMessage("password.changePassword.passwordChanged"));
		return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/requestPasswordResetToken", method = RequestMethod.GET)
	public ResponseEntity<ApiJsonResponse> requestPasswordResetToken(@RequestParam(name="email", required=true) String email) throws UnsupportedEncodingException{
		ApiJsonResponse response;
		User user = userService.findUserByEmail(email);
		if(user == null){
			response = new ApiJsonResponse(false, messageLookup.getMessage("password.requestPasswordResetToken.invalidEmail"));
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		response = new ApiJsonResponse(true, messageLookup.getMessage("password.requestPasswordResetToken.emailSent"));
		return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/resetPasswordByPasswordResetToken/{id}/{token}", method = RequestMethod.POST)
	public ResponseEntity<ApiJsonResponse> resetPassword(@PathVariable Long id, @PathVariable String token,
			@RequestBody Map<String, Object> model) throws InvalidPasswordStrengthException{
		
		String newPassword = null;	
		if(model.containsKey("newPassword")){
			newPassword= model.get("newPassword").toString();
		}
		
		if(StringUtils.isBlank(newPassword)){
			ApiJsonResponse response = new ApiJsonResponse(false, messageLookup.getMessage("password.resetPassword.emptyNewPassword"));
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		String jsonresponse = userService.validatePasswordResetToken(id, token);
		
		if(jsonresponse != null){
			ApiJsonResponse response = new ApiJsonResponse(false, jsonresponse);
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		userService.changePassword(newPassword, id);
		
		userService.removePasswordResetToken(token);
		
		ApiJsonResponse response = new ApiJsonResponse(true, messageLookup.getMessage("password.resetPassword.passwordChanged"));
		return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.OK);
		
	}
	//========================= Role Management =======================//
	
	@PreAuthorize("hasAuthority('CREATE_A_ROLE')")
	@RequestMapping(value = "/role/add", method = RequestMethod.POST)
	public ResponseEntity<ApiJsonResponse> createRole(@RequestBody RoleDTO dto) {
		ApiJsonResponse response;
		if(StringUtils.isBlank(dto.getName())|| StringUtils.containsWhitespace(dto.getName())){
			response = new ApiJsonResponse(false, messageLookup.getMessage("role.createRole.emptyRoleName"));
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		// Checks for avoiding duplicate role names
		if (roleService.isRoleDuplicate(dto.getName())) {
			response = new ApiJsonResponse(false, messageLookup.getMessage("role.createRole.dulicateRole"));
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		if(dto.getPermissions() == null || dto.getPermissions().isEmpty()){
			response = new ApiJsonResponse(false, messageLookup.getMessage("role.createRole.numberOfPermission"));
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		} else{
			for(PermissionDTO permissionDto : dto.getPermissions()){
				if(!roleService.isPermissionValid(permissionDto.getAuthority())){
					response = new ApiJsonResponse(false, messageLookup.getMessage("role.createRole.invalidPermission"));
					return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			}
		}
		
		if(dto.isEnabled() == null){
			dto.setEnabled(true);
		}
		
		Role newRole = new Role();
		newRole.setAuthority(dto.getName());
		newRole.setDescription(dto.getDescription());
		newRole.setEnabled(dto.isEnabled());
		if(dto.getPermissions() != null && !dto.getPermissions().isEmpty())
		{
			List<Permission> permissions = new ArrayList<>();
			dto.getPermissions().forEach(permissionDto -> {
				Permission permission = roleService.findPermissionById(permissionDto.getAuthority());
				permissions.add(permission);
			});
			
			newRole.setPermissions(permissions);
		}
		
		roleService.createRole(newRole);
		
		response = new ApiJsonResponse(true, messageLookup.getMessage("role.createRole.roleCreated", new Object[]{newRole.getAuthority()}));
		return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.OK);
	}

}
