package com.swivel.qpon.otp.wrapper;

import com.swivel.qpon.otp.enums.ErrorResponseStatusType;
import com.swivel.qpon.otp.enums.ResponseStatusType;
import lombok.Getter;

/**
 * ErrorResponseWrapper
 */
@Getter
public class ErrorResponseWrapper extends ResponseWrapper {

    private final int errorCode;
    private final String displayMessage;

    public ErrorResponseWrapper(ErrorResponseStatusType errorResponseStatusType, String message, String data) {
        super(ResponseStatusType.ERROR, errorResponseStatusType.getMessage(), data);
        this.errorCode = errorResponseStatusType.getCode();
        this.displayMessage = message;
    }

}
    