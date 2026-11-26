package com.hulkhiretech.payment.constant;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
	GENERAL_ERROR("1000", "Something went wrong. Please try again later"),
	INVALID_FIRST_NAME("1001", "Invalid First Name contains 'TEMP'"),
	VALIDATOR_CLASS_NOT_FOUND("1002", "No validator class found for the provided name"),
	JSON_SERIALIZATION_ERROR("1003", "Unable to serialize response"),
	INVALID_JSON_REQUEST("1004", "Invalid JSON request body");

	private final String errorCode;
	private final String errorMessage;

	ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
