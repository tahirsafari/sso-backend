package com.ssobackend.exception;

@SuppressWarnings("serial")
public class InvalidPasswordStrengthException extends Exception {
	// Parameterless Constructor
    public InvalidPasswordStrengthException() {}

    // Constructor that accepts a message
    public InvalidPasswordStrengthException(String message)
    {
       super(message);
    }

}
