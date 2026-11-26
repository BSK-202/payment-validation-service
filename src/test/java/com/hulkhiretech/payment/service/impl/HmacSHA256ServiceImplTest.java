package com.hulkhiretech.payment.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import com.hulkhiretech.payment.constant.ErrorCodeEnum;
import com.hulkhiretech.payment.exception.ValidationException;

class HmacSHA256ServiceImplTest {

	private HmacSHA256ServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new HmacSHA256ServiceImpl();
		ReflectionTestUtils.setField(service, "secretKey", "test-secret");
	}

	@Test
	void verifySignatureRejectsMissingSignature() {
		ValidationException exception = assertThrows(
				ValidationException.class,
				() -> service.verifySignature("{}", null));

		assertEquals(ErrorCodeEnum.MISSING_HMAC_SIGNATURE.getErrorCode(), exception.getErrorCode());
		assertEquals(ErrorCodeEnum.MISSING_HMAC_SIGNATURE.getErrorMessage(), exception.getErrorMessage());
		assertEquals(HttpStatus.UNAUTHORIZED, exception.getHttpStatus());
	}

	@Test
	void verifySignatureRejectsMismatchedSignature() {
		ValidationException exception = assertThrows(
				ValidationException.class,
				() -> service.verifySignature("{}", "incorrect-signature"));

		assertEquals(ErrorCodeEnum.INVALID_HMAC_SIGNATURE.getErrorCode(), exception.getErrorCode());
		assertEquals(ErrorCodeEnum.INVALID_HMAC_SIGNATURE.getErrorMessage(), exception.getErrorMessage());
		assertEquals(HttpStatus.UNAUTHORIZED, exception.getHttpStatus());
	}

	@Test
	void verifySignatureAcceptsMatchingSignature() {
		String signature = service.generateSignature("{}");

		assertDoesNotThrow(() -> service.verifySignature("{}", signature));
	}

	@Test
	void generateSignatureReportsSpecificFailure() {
		ValidationException exception = assertThrows(
				ValidationException.class,
				() -> service.generateSignature(null));

		assertEquals(
				ErrorCodeEnum.HMAC_SIGNATURE_GENERATION_FAILED.getErrorCode(),
				exception.getErrorCode());
		assertEquals(
				ErrorCodeEnum.HMAC_SIGNATURE_GENERATION_FAILED.getErrorMessage(),
				exception.getErrorMessage());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
	}
}
