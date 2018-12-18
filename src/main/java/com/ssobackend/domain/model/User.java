package com.ssobackend.domain.model;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "user")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String password;
	
	private boolean enabled;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private Date passwordChangedDate;
	
	private List<Role> roles;
	
    public User(){
    	
    }
    
    public User(Long id, String username){
    	this.id = id;
    	this.username = username;
    	this.accountNonLocked = true;
    	this.enabled = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    
    public void setId(Long id){
    	this.id=id;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	@ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public List<Role> getAuthorities() {
		if(this.roles == null)
			this.roles = new ArrayList<>();
		return this.roles;
	}
	
	public void setAuthorities(List<Role> newRoles){
		this.roles = newRoles;
	}
	
	//Indicates whether the user's account has expired. An expired account cannot be authenticated.
	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}
	
	public void setAccountNonLocked(boolean flag){
		this.accountNonLocked = flag;
	}
	
	//Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	
	public void setCredentialsNonExpired(boolean flag){
		this.credentialsNonExpired = flag;
	}
	
	//Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	@Temporal(TIMESTAMP)
	public Date getPasswordChangedDate() {
		return passwordChangedDate;
	}

	public void setPasswordChangedDate(Date passwordChangeDate) {
		this.passwordChangedDate = passwordChangeDate;
	}
	
}
