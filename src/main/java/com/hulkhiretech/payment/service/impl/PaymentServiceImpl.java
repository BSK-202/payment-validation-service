package com.hulkhiretech.payment.service.impl;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payment.pojo.PaymentReq;
import com.hulkhiretech.payment.service.interfaces.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
	public String createPayment(PaymentReq paymentReq) {
		log.info("Starting createPayment in PaymentService");
		
		
		return "HMAC signature validation successful. Proceeding with payment processing.";
	}

	
}