package com.ssobackend.domain.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssobackend.SsoBackendApplication;
import com.ssobackend.domain.dto.CreateCustomerDto;
import com.ssobackend.domain.dto.UpdateCustomerDto;
import com.ssobackend.domain.model.Customer;
import com.ssobackend.domain.service.CustomerService;

@RunWith(SpringRunner.class)
@ActiveProfiles(value="test")
@SpringBootTest
public class CustomerControllerTests {

	@Autowired
	WebApplicationContext wac;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private CustomerService customerService;
	private MockMvc mockMvc;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
        		.webAppContextSetup(wac)
                .build();
	}
	
	
	@Before
	public void addData(){
		
		Customer customer = new Customer();
		customer.setAddress("lorem ipsum");
		customer.setContactNumber("+92-300-13344899");
		customer.setFirstName("David");
		customer.setLastName("Lorem");
		
		customerService.saveCustomer(customer);
	}
	
	@Test
	public void list_all_customers_success() throws Exception{
		mockMvc.perform(get("/customers/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				//.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id").value(1L))
				.andExpect(jsonPath("$[0].firstName").value("David"))
				.andDo(print());
	}
	
	@Test
	public void save_customer_success() throws Exception{
		CreateCustomerDto newCustomer = new CreateCustomerDto("Customer First Name","Customer Last Name", "Customer Address", "Customer Contact" );
		mockMvc.perform(post("/customers/save")
		        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		        .content(objectMapper.writeValueAsString(newCustomer)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));
	}
	
	@Test
	public void customer_found_success() throws Exception{
		mockMvc.perform(get("/customers/id/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		  		.andExpect(status().isOk()).andExpect(jsonPath("$.address").isNotEmpty())
		  		.andExpect(jsonPath("$.id").value(1L));
	}
	
	@Test
	public void customer_not_found_failure() throws Exception{
		mockMvc.perform(get("/customers/id/{id}", 200L)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		  		.andExpect(status().isNotFound())
		  		.andExpect(jsonPath("$.success").value(false));
	}
	
	@Test
	public void update_customer_success() throws Exception{
		UpdateCustomerDto updatedCustomer = new UpdateCustomerDto(1L, "Customer First Name Updated","Customer Last Name Updated" );
		mockMvc.perform(put("/customers/update")
		        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		        .content(objectMapper.writeValueAsString(updatedCustomer)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));
	}
}
