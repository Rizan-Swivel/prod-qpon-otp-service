package com.swivel.qpon.otp.exception;

/**
 * Exception for Notification template type enum validation
 */
public class InvalidNotificationTemplateType extends OTPServiceException {
    public InvalidNotificationTemplateType(String errorMessage) {
        super(errorMessage);
    }
}
