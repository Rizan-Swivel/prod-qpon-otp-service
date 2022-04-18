package com.swivel.qpon.otp.wrapper;

import com.swivel.qpon.otp.domain.BaseDto;
import com.swivel.qpon.otp.enums.ResponseStatusType;
import lombok.Getter;
import lombok.Setter;

/**
 * Send sms wrapper to get sms send response from util service
 */
@Setter
@Getter
public class SendSmsWrapper implements BaseDto {

    private ResponseStatusType status;
    private String message;
    private String displayMessage;

    @Override
    public String toLogJson() {
        return toJson();
    }
}
