package com.swivel.qpon.otp.exception;

import com.swivel.qpon.otp.service.OtpService;

/**
 * This Exception is used in {@link OtpService}
 */
public class InvalidOtpKeyException extends OTPServiceException {

    /**
     * Invalid otp key exception
     *
     * @param errorMessage error Message
     */
    public InvalidOtpKeyException(String errorMessage) {
        super(errorMessage);
    }
}
