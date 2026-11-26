package com.hulkhiretech.payment.util;

import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulkhiretech.payment.constant.ErrorCodeEnum;
import com.hulkhiretech.payment.exception.ValidationException;
import com.hulkhiretech.payment.pojo.ErrorResponse;

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
            throw new ValidationException(
                    ErrorCodeEnum.INVALID_JSON_REQUEST.getErrorCode(),
                    ErrorCodeEnum.INVALID_JSON_REQUEST.getErrorMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        LinkedHashMap<String, Object> map;
        try {
            map = objectMapper.readValue(body, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            log.error("Invalid JSON request body", e);
            throw new ValidationException(
                    ErrorCodeEnum.INVALID_JSON_REQUEST.getErrorCode(),
                    ErrorCodeEnum.INVALID_JSON_REQUEST.getErrorMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error("Error while formatting JSON body", e);
            throw new ValidationException(
                    ErrorCodeEnum.GENERAL_ERROR.getErrorCode(),
                    ErrorCodeEnum.GENERAL_ERROR.getErrorMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String convertObjectToJson(ErrorResponse errorResponse) {
        try {
            return objectMapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize error response to JSON", e);
            throw new ValidationException(
                    ErrorCodeEnum.JSON_SERIALIZATION_ERROR.getErrorCode(),
                    ErrorCodeEnum.JSON_SERIALIZATION_ERROR.getErrorMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
