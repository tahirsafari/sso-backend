package com.ssobackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.ssobackend.domain.dto.CreateCustomerDto;
import com.ssobackend.domain.dto.CustomerDetailsDto;
import com.ssobackend.domain.dto.CustomerListDto;
import com.ssobackend.domain.dto.UpdateCustomerDto;
import com.ssobackend.domain.model.Customer;
import com.ssobackend.domain.service.CustomerService;
import com.ssobackend.exception.InvalidParamException;
import com.ssobackend.util.ApiJsonResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@PreAuthorize("hasAuthority('VIEW_ALL_CUSTOMERS')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CustomerListDto> getAllCustomers() {
    	List<Customer> customers = customerService.getAllCustomers();
    	List<CustomerListDto> customerList = new ArrayList<>();
    	customers.forEach(customer ->{
    		CustomerListDto customerDto = new CustomerListDto(customer.getId(), customer.getFirstName(), customer.getLastName());
    		customerList.add(customerDto);
    	});
    	
        return customerList;
    }
    
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<ApiJsonResponse> saveCustomer(@Valid @RequestBody CreateCustomerDto dto) { //throws InvalidParamException{
		Customer customer = new Customer();
		customer.setFirstName(dto.getFirstName());
		customer.setLastName(dto.getLastName());
		customer.setAddress(dto.getContactNumber());
		customer.setContactNumber(dto.getContactNumber());
		
		customerService.saveCustomer(customer);
		ApiJsonResponse response = new ApiJsonResponse(true, "Customer Added");
		return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('VIEW_A_CUSTOMER')")
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
    	Customer customer = customerService.getCustomer(id);
    	if(customer != null) {
    		CustomerDetailsDto dto = new CustomerDetailsDto(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getContactNumber(), customer.getAddress());
    		return new ResponseEntity<CustomerDetailsDto>(dto, HttpStatus.OK);
    	}
    	
    	ApiJsonResponse response = new ApiJsonResponse(false, "No Customer Found");
		return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_FOUND);
    }
    
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<ApiJsonResponse> updateCustomer(@Valid @RequestBody UpdateCustomerDto dto) throws InvalidParamException{
		Customer customer = customerService.getCustomer(dto.getId());
		if(customer != null) {
			customer.setFirstName(dto.getFirstName());
			customer.setLastName(dto.getLastName());
			customerService.saveCustomer(customer);
			ApiJsonResponse response = new ApiJsonResponse(true, "Customer updated");
			return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.OK);
		}
		
		ApiJsonResponse response = new ApiJsonResponse(false, "Customer Not Found");
		return new ResponseEntity<ApiJsonResponse>(response, HttpStatus.NOT_FOUND);

	}
	
}
