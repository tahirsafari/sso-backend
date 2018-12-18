package com.ssobackend.exception;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Temporal;

import org.springframework.http.HttpStatus;

class ExceptionJsonResponse {
	private Boolean success;
	private HttpStatus status;
	private String message;
	private String details;
	
	public ExceptionJsonResponse(HttpStatus status, String message, String exceptionDetail) {
		super();
		this.success = false;
		this.status = status;
		this.message = exceptionDetail;
		this.details = exceptionDetail;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String exceptionDetail) {
		this.message = exceptionDetail;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String exceptionDetail) {
		this.details = exceptionDetail;
	}
	
	@Temporal(TIMESTAMP)
	public Date getTimeStamp() {
		return new Date();
	}
}
