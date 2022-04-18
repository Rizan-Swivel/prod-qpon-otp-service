package com.swivel.qpon.otp.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swivel.qpon.otp.exception.OTPServiceException;

/**
 * BaseDto
 */
public interface BaseDto {

    /**
     * This method converts object to json string.
     *
     * @return json string
     */
    default String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new OTPServiceException("Object to json conversion was failed.", e);
        }
    }

    /**
     * This method converts object to json string for logging purpose.
     * PII data should be obfuscated.
     *
     * @return json string
     */
    String toLogJson();

}
