package com.hulkhiretech.payment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payment.pojo.PaymentReq;
import com.hulkhiretech.payment.service.interfaces.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostMapping
	public String createPayment(
			@RequestBody PaymentReq paymentReq,
			@RequestHeader("HMAC-Signature") String hmacSignature) {

		log.info("Received HMAC-Signature header: {}", hmacSignature);
		log.info("Received PaymentReq: {}", paymentReq);

		String response = paymentService.createPayment(paymentReq, hmacSignature);

		log.info("Completed createPayment in PaymentController\n:{}", response);
		return response;
	}

	@GetMapping("/{id}")
	public String getPayment(@PathVariable("id") String paymentId) {

		log.info("Received paymentId: {}", paymentId);

		log.info("Completed getPayment in PaymentController\n:{}", paymentId);
		return paymentId;
	}
}
