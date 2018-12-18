package com.ssobackend.domain.dto;

public class CustomerDetailsDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String fullName;
	private String contactNumber;
	private String address;
	
	public CustomerDetailsDto() {}
	public CustomerDetailsDto(Long id, String firstName, String lastName, String contactNumber, String address ) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
		this.address = address;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFullName() {
		return firstName+ " "+lastName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
