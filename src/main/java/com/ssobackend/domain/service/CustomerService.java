package com.ssobackend.domain.service;

import java.util.List;

import com.ssobackend.domain.model.Customer;

public interface CustomerService {
	public void saveCustomer(Customer customer);
	public List<Customer> getAllCustomers();
	public  Customer getCustomer(Long id);
	public void updateCustomer(Customer customer);
}
