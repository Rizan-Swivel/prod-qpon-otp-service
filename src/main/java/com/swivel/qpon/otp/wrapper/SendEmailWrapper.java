package com.swivel.qpon.otp.wrapper;

import com.swivel.qpon.otp.domain.BaseDto;
import com.swivel.qpon.otp.enums.ResponseStatusType;
import lombok.Getter;
import lombok.Setter;

/**
 * Send email wrapper to get email send response from util service
 */
@Getter
@Setter
public class SendEmailWrapper implements BaseDto {

    private ResponseStatusType status;
    private String message;
    private String displayMessage;

    @Override
    public String toLogJson() {
        return toJson();
    }
}
