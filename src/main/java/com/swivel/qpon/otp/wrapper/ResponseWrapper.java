package com.swivel.qpon.otp.wrapper;

import com.swivel.qpon.otp.domain.BaseDto;
import com.swivel.qpon.otp.enums.ResponseStatusType;
import lombok.Getter;

/**
 * ResponseWrapper
 */
@Getter
public class ResponseWrapper implements BaseDto {

    private ResponseStatusType status;
    private String message;
    private String data;

    public ResponseWrapper(ResponseStatusType statusType, String message, String data) {
        this.status = statusType;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toLogJson() {
        return toJson();
    }
}

