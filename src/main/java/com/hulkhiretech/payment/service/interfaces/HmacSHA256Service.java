package com.hulkhiretech.payment.service.interfaces;

public interface HmacSHA256Service {

	String generateSignature(String inputJson);
	void verifySignature(String inputJson, String incomingHmacSignature);

}
