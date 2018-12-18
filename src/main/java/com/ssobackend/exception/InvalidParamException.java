package com.ssobackend.exception;

@SuppressWarnings("serial")
public class InvalidParamException extends Exception{
	public InvalidParamException(){
		
	}
	
	public InvalidParamException(String message){
		super(message);
	}
}