package com.swivel.qpon.otp.enums;

import lombok.Getter;

/**
 * Error response status type
 */
@Getter
public enum ErrorResponseStatusType {

    MISSING_REQUIRED_FIELDS(4000, "Required fields are missing"),
    INVALID_MOBILE_NO(4101, "Invalid mobile number"),
    INVALID_EMAIL(4102, "Invalid email address"),
    MULTIPLE_TYPES_NOT_SUPPORTED(4400, "Multiple types are not supported"),
    INVALID_OTP(4901, "Invalid otp"),
    INVALID_EMAIL_OR_EXPIRED_OTP(4902, "Invalid email or expired OTP."),
    INVALID_MOBILE_NUMBER_OR_EXPIRED_OTP(4903, "Invalid mobile number or expired OTP"),
    INTERNAL_SERVER_ERROR(5000, "Failed due to an internal server error");

    private final int code;
    private final String message;

    ErrorResponseStatusType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Return String value of code to match with resource-message property key
     *
     * @param code code
     * @return code
     */
    public String getCodeString(int code) {
        return String.valueOf(code);
    }
}
