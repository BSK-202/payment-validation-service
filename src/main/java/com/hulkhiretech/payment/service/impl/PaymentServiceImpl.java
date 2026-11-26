package com.hulkhiretech.payment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payment.constant.ValidatorRuleEnum;
import com.hulkhiretech.payment.pojo.PaymentReq;
import com.hulkhiretech.payment.service.interfaces.BusinessValidator;
import com.hulkhiretech.payment.service.interfaces.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	@Value("${validator.rulelist}")
	private String validatorRuleList;

	private final ApplicationContext applicationContext;

	public PaymentServiceImpl(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public String createPayment(PaymentReq paymentReq) {
		log.info("Starting createPayment in PaymentService");
		log.info("=====>Validator rule list from properties: {}", validatorRuleList);

		// split the validatorRuleList and put into a linked list
		List<String> validatorRules = List.of(validatorRuleList.split(","));

		validatorRules.forEach(rule -> {
			log.info("Validator rule: {}", rule.trim());

			Class<? extends BusinessValidator> validatorClass = ValidatorRuleEnum
					.getValidatorClass(rule);

			BusinessValidator validator = applicationContext.getBean(validatorClass);
			log.info("Executing validator: {}", validator.getClass().getSimpleName());

			validator.validate(paymentReq);
		});

		log.info("All validators executed successfully. Proceeding with payment processing.");

		// TODO: Implement the next mcicroservice call to process the payment after
		// successful validation
		// Invoke Stripe provider Microservice to process the payment

		return "HMAC signature validation successful. Proceeding with payment processing.";
	}

}