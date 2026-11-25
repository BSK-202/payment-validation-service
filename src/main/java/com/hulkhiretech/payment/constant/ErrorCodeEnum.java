package com.hulkhiretech.payment.constant;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
	GENERAL_ERROR("1000", "Something went wrong. Please try again later"),
	INVALID_FIRST_NAME("1001", "Invalid First Name contains 'TEMP'"),
	FIRST_NAME_NULL("1002", "First name cannot be null");

	private final String errorCode;
	private final String errorMessage;

	ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}


}
