package com.hulkhiretech.payment.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String errorCode;
	private final String errorMessage;
	private final HttpStatus httpStatus;

	public ValidationException(String errorCode, String errorMessage, HttpStatus httpStatus) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}
}
