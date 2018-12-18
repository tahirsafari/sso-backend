package com.ssobackend.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssobackend.domain.model.Customer;
import com.ssobackend.domain.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public void saveCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return (List<Customer>) customerRepository.findAll();
	}

	@Override
	public Customer getCustomer(Long id) {
		return customerRepository.findOne(id);
	}

	@Override
	public void updateCustomer(Customer customer) {
		customerRepository.save(customer);
	}

}
