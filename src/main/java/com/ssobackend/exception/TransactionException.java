package com.ssobackend.exception;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class TransactionException extends AuthenticationException{

	public TransactionException(String msg) {
		super(msg);
	}
}
