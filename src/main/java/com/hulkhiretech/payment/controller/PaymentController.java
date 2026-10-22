package com.hulkhiretech.payment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payment.service.interfaces.PaymentService;

@Slf4j
@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostMapping
	public void createPayment() {
		log.info("Starting createPayment in PaymentController");
		paymentService.createPayment();
		log.info("Completed createPayment in PaymentController");
	}
}
