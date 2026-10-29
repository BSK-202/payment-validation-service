package com.hulkhiretech.payment.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payment.pojo.PaymentReq;
import com.hulkhiretech.payment.service.interfaces.BusinessValidator;
import com.hulkhiretech.payment.service.interfaces.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	private final ApplicationContext applicationContext;

	public PaymentServiceImpl(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public String createPayment(PaymentReq paymentReq) {
		log.info("Starting createPayment in PaymentService");

		List<BusinessValidator> businessValidators = List.of(
				applicationContext.getBean(ValidatorCheck1.class),
				applicationContext.getBean(ValidatoCheck2.class));

		for (BusinessValidator validator : businessValidators) {
			log.info("Running validator: {}", validator.getClass().getSimpleName());
			validator.validate(paymentReq);
		}

		return "HMAC signature validation successful. Proceeding with payment processing.";
	}

	
}