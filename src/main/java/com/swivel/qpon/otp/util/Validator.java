package com.swivel.qpon.otp.util;

import org.springframework.stereotype.Component;

/**
 * Validation Class
 */
@Component
public class Validator {

    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    private static final String MOBILE_NO_REGEX = "^\\+(\\d{2}[-])\\d{9}$";
    private static final String OTP_REGEX = "^([0-9]{6})$";

    /**
     * This method validates a given email.
     *
     * @param email email address
     * @return true/ false
     */
    public boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    /**
     * This method validates a given mobile no.
     *
     * @param mobileNo mobile no
     * @return true/ false
     */
    public boolean isValidMobileNo(String mobileNo) {
        return mobileNo.matches(MOBILE_NO_REGEX);
    }

    /**
     * This method validates a given otp
     *
     * @param otp otp
     * @return true/ false
     */
    public boolean isValidOtp(String otp) {
        return otp.matches(OTP_REGEX);
    }

}
