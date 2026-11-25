package com.hulkhiretech.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hulkhiretech.payment.constant.ErrorCodeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationException(
			ValidationException exception) {

		log.error("Validation error occureed. Error code :{}, Error Message:{} ", 
		               exception.getErrorCode(), exception.getErrorMessage());

		ValidationErrorResponse response = new ValidationErrorResponse(
				exception.getErrorCode(),
				exception.getErrorMessage());

		return ResponseEntity.status(exception.getHttpStatus()).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ValidationErrorResponse> handleException(Exception exception) {
		log.error("Unhandled exception while processing request", exception);

		ValidationErrorResponse response = new ValidationErrorResponse(
				ErrorCodeEnum.GENERAL_ERROR.getErrorCode(),
				ErrorCodeEnum.GENERAL_ERROR.getErrorMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	public record ValidationErrorResponse(
			String errorCode,
			String errorMessage) {
	}
}
