package com.ssobackend.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NonUniqueResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ssobackend.util.ApiMessageLookup;

@ControllerAdvice
public class CustomExcpetionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private ApiMessageLookup messageLookup;

	// ===================== 404 : Handler Not Found
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		ex.printStackTrace();
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		final ExceptionJsonResponse response = new ExceptionJsonResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(),
				error);

		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}

	// ===================== 400 : Validation Exception
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BindingResult result = ex.getBindingResult();
		List<String> errorMsgsList = new ArrayList<>();
		for (FieldError error : result.getFieldErrors()) {
			StringBuilder msg = new StringBuilder();

			msg.append(error.getField()).append(": ").append(messageLookup.getMessage(error.getDefaultMessage()));

			errorMsgsList.add(msg.toString());
		}
		String errorMsgsString = errorMsgsList.stream().collect(Collectors.joining("; "));

		ExceptionJsonResponse response = new ExceptionJsonResponse(HttpStatus.BAD_REQUEST, "Validation exception",
				errorMsgsString);

		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	// ===================== 500 : Non Unique Result Exception
	@ExceptionHandler({ NonUniqueResultException.class })
	public ResponseEntity<Object> handleNonUniqueResultException(final NonUniqueResultException ex,
			final WebRequest request) {
		ex.printStackTrace();
		final ExceptionJsonResponse response = new ExceptionJsonResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				"Duplicate records found", ex.getLocalizedMessage());
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ===================== 406 : Invalid Password Strength
	@ExceptionHandler({ InvalidPasswordStrengthException.class })
	public ResponseEntity<Object> invalidPasswordStrengthException(final InvalidPasswordStrengthException ex,
			final WebRequest request) {
		ex.printStackTrace();
		final ExceptionJsonResponse response = new ExceptionJsonResponse(HttpStatus.NOT_ACCEPTABLE, "Invalid Password",
				ex.getLocalizedMessage());
		return new ResponseEntity<Object>(response, HttpStatus.NOT_ACCEPTABLE);
	}

	// ==================== 500 : JPA Uncaught Exceptions
	@ExceptionHandler({ JpaSystemException.class })
	public ResponseEntity<Object> handleJPAException(final JpaSystemException ex, final WebRequest request) {
		ex.printStackTrace();
		String rootCause = ExceptionUtils.getRootCause(ex).getMessage();
		final ExceptionJsonResponse response = new ExceptionJsonResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				"JPA Exception Occurred", rootCause);
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ==================== 401: Access Denied Exception
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> accessDeniedException(final AccessDeniedException ex) {
		ex.printStackTrace();
		final ExceptionJsonResponse response = new ExceptionJsonResponse(HttpStatus.UNAUTHORIZED, "Unauthorized",
				ex.getLocalizedMessage());
		return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
	}
	
	// ==================== Data Integrity Violation Exception
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(final DataIntegrityViolationException ex,
			final WebRequest request) {
		ex.printStackTrace();

		final ExceptionJsonResponse response = new ExceptionJsonResponse(HttpStatus.BAD_REQUEST, "Constraint Violated",
				ex.getMostSpecificCause().toString());
		return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	// ===================== Constrain violation Exception
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
			final WebRequest request) {
		ex.printStackTrace();
		StringBuilder sb = new StringBuilder();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			sb.append(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		final ExceptionJsonResponse response = new ExceptionJsonResponse(HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage(), sb.toString());

		return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	// ===================== Constrain violation Exception
	@ExceptionHandler({ InvalidParamException.class })
	public ResponseEntity<Object> handleInvalidParamException(final InvalidParamException ex,
			final WebRequest request) {
		ex.printStackTrace();
		final ExceptionJsonResponse response = new ExceptionJsonResponse(HttpStatus.NOT_ACCEPTABLE,
				"Data not Acceptable", ex.getMessage());
		return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
	}

	// ==================== 500 : Uncaught typed Exceptions
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		ex.printStackTrace();
		final ExceptionJsonResponse response = new ExceptionJsonResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				"Exception Occurred", ex.getLocalizedMessage());
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
