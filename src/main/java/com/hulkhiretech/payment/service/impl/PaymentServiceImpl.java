package com.hulkhiretech.payment.service.impl;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulkhiretech.payment.pojo.PaymentReq;
import com.hulkhiretech.payment.service.interfaces.HmacSHA256Service;
import com.hulkhiretech.payment.service.interfaces.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	private final ObjectMapper objectMapper;
	private final HmacSHA256Service hmacSHA256Service ;

	public PaymentServiceImpl(ObjectMapper objectMapper, 
		HmacSHA256Service hmacSHA256Service) {

		this.objectMapper = objectMapper;
		this.hmacSHA256Service = hmacSHA256Service;
	}

	@Override
	public String createPayment(PaymentReq paymentReq, String incomingHmacSignature) {
		log.info("Starting createPayment in PaymentService");
		
		log.info("Incoming HMAC Signature: {}", incomingHmacSignature);

		String jsonReq = convertPaymentReqToJson(paymentReq);
		log.info("Serialized PaymentReq to JSON: \n" + jsonReq);

		hmacSHA256Service.verifySignature(jsonReq, incomingHmacSignature);

		
		return "HMAC signature validation successful. Proceeding with payment processing.";
	}

	private String convertPaymentReqToJson(PaymentReq paymentReq) {
		try {
			String jsonString = objectMapper.writeValueAsString(paymentReq);
			log.info("Serialized PaymentReq to JSON: \n" + jsonString);
			return jsonString;
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("Failed to serialize payment request", e);
		}
	}
}