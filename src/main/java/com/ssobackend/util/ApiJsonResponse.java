package com.ssobackend.util;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Temporal;

public class ApiJsonResponse {
	private Boolean success;
	private String message;
	
	public ApiJsonResponse(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Temporal(TIMESTAMP)
	public Date getTimeStamp() {
		return new Date();
	}
}