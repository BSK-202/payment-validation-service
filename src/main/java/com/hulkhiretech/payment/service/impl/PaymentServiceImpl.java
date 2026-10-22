package com.hulkhiretech.payment.service.impl;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payment.service.interfaces.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
	public void createPayment() {
		log.info("Starting createPayment in PaymentService");
		log.info("Completed createPayment in PaymentService");

	}
}