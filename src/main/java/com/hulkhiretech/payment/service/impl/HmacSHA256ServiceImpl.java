
package com.hulkhiretech.payment.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payment.constant.Constant;
import com.hulkhiretech.payment.service.interfaces.HmacSHA256Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HmacSHA256ServiceImpl implements HmacSHA256Service {

	@Value("${hmac.secret-key}")
	private String secretKey;

	@Override
	public String generateSignature(String inputJson) {
		try {
			Mac mac = Mac.getInstance(Constant.HMAC_SHA256);

			SecretKeySpec secretKeySpec = new SecretKeySpec(
					secretKey.getBytes(StandardCharsets.UTF_8),
					Constant.HMAC_SHA256);

			mac.init(secretKeySpec);

			byte[] hashSignature = mac.doFinal(
					inputJson.getBytes(StandardCharsets.UTF_8));

			return Base64.getEncoder().encodeToString(hashSignature);

		} catch (Exception e) {
			throw new RuntimeException(
					"Failed to generate HMAC-SHA256 signature", e);
		}
	}

	@Override
	public void verifySignature(String inputJson, String incomingHmacSignature) {
		String generatedSignature = generateSignature(inputJson);

		if (!generatedSignature.equals(incomingHmacSignature)) {
			throw new IllegalArgumentException("HMAC signature mismatch");
		}
	}
}