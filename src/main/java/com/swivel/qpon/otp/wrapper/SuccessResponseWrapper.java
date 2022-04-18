package com.swivel.qpon.otp.wrapper;

import com.swivel.qpon.otp.enums.ResponseStatusType;
import com.swivel.qpon.otp.enums.SuccessResponseStatusType;
import lombok.Getter;

/**
 * Success Response Wrapper
 */
@Getter
public class SuccessResponseWrapper extends ResponseWrapper {

    private final String displayMessage;

    public SuccessResponseWrapper(SuccessResponseStatusType successResponseStatusType, String message, String data) {
        super(ResponseStatusType.SUCCESS, successResponseStatusType.getMessage(), data);
        this.displayMessage = message;
    }
}
