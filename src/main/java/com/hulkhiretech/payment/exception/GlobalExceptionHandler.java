package com.hulkhiretech.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hulkhiretech.payment.constant.ErrorCodeEnum;
import com.hulkhiretech.payment.pojo.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(
			ValidationException exception) {

		log.error("Validation error occureed. Error code :{}, Error Message:{} ", 
		               exception.getErrorCode(), exception.getErrorMessage());

		ErrorResponse response = new ErrorResponse(
				exception.getErrorCode(),
				exception.getErrorMessage());

		return ResponseEntity.status(exception.getHttpStatus()).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		log.error("Unhandled exception while processing request", exception);

		ErrorResponse response = new ErrorResponse(
				ErrorCodeEnum.GENERAL_ERROR.getErrorCode(),
				ErrorCodeEnum.GENERAL_ERROR.getErrorMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	
}
