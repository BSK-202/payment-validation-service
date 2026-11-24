package com.hulkhiretech.payment.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payment.exception.ValidationException;
import com.hulkhiretech.payment.pojo.PaymentReq;
import com.hulkhiretech.payment.service.interfaces.BusinessValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ValidatorCheck1 implements BusinessValidator {

	@Override
	public void validate(PaymentReq paymentReq) {
		log.info("Executing validate in ValidatorCheck1 {}: ", paymentReq);

		String name = null;
		name.length();

		if (paymentReq.getUser().getFirstName().contains("TEMP")) {

			throw new ValidationException("1001", "Invalid First Name contains 'TEMP'", HttpStatus.BAD_REQUEST);
		}
	}
}
