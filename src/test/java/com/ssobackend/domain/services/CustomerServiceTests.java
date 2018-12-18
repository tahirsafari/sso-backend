package com.ssobackend.domain.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.ssobackend.domain.model.Customer;
import com.ssobackend.domain.service.CustomerService;
import com.ssobackend.domain.service.CustomerServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTests {

	private CustomerService customerService;
	@Before
	public void init() {
		customerService = mock(CustomerServiceImpl.class);
	}
	@Test
	public void saveCustomer() {
		Customer customer = new Customer();
		customer.setAddress("address");
		customer.setContactNumber("contactNumber");
		customer.setFirstName("firstName");
		customer.setLastName("lastName");
		
		customerService.saveCustomer(customer);
	}
	
	@Test
	public void getCustomers() {
		Customer customer = new Customer();
		customer.setAddress("address");
		customer.setContactNumber("contactNumber");
		customer.setFirstName("firstName");
		customer.setLastName("lastName");
		
		Customer customer2 = new Customer();
		customer2.setAddress("address");
		customer2.setContactNumber("contactNumber");
		customer2.setFirstName("firstName");
		customer2.setLastName("lastName");
		
		List<Customer> customerList = new ArrayList<>();
		customerList.add(customer);
		customerList.add(customer2);
		
		when(customerService.getAllCustomers()).thenReturn(customerList);
		assertEquals(2, customerService.getAllCustomers().size());
	}
	
	@Test
	public void getCustomer() {
		Customer customer = new Customer();
		customer.setAddress("address");
		customer.setContactNumber("contactNumber");
		customer.setFirstName("firstName");
		customer.setLastName("lastName");

		
		when(customerService.getCustomer(1L)).thenReturn(customer);
		assertEquals(customer, customerService.getCustomer(1L));
		assertEquals(customer.getFirstName(), customerService.getCustomer(1L).getFirstName());
	}
}
