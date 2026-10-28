package com.hulkhiretech.payment.util;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JsonUtil {

	private final ObjectMapper objectMapper;

	public JsonUtil(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public String prepareFormattedJson(String body) {
        if (body == null || body.isBlank()) {
            throw new IllegalArgumentException("Request body is null or empty");
        }

        try {
            // Parse JSON while preserving order
            LinkedHashMap<String, Object> map = 
            objectMapper.readValue(body, LinkedHashMap.class);
            // Serialize back to JSON
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            log.error("Error while formatting JSON body: {}", e.getMessage(), e);
            throw new RuntimeException("Error while formatting JSON body", e);
        }
    }
}
