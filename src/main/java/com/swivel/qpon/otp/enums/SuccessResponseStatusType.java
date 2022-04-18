package com.swivel.qpon.otp.enums;


import lombok.Getter;

/**
 * Success Response Status Type
 */
@Getter
public enum SuccessResponseStatusType {

    SENT_OTP(2000, "OTP request was sent Successfully."),
    VERIFIED_OTP(2001, "Successfully verified user."),
    EMAIL_SUCCESS_MESSAGE(2002, "Please use the following OTP to complete your email verification"),
    SMS_SUCCESS_MESSAGE(2003, "Please use the following OTP to complete your mobile verification"),
    EMAIL_SUBJECT(2004, "Tokobook Email Verification");

    private final String code;
    private final String message;

    SuccessResponseStatusType(int code, String message) {
        this.code = getCodeString(code);
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
