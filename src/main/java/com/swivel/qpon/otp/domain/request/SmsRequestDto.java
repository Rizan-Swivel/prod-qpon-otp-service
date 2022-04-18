package com.swivel.qpon.otp.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Sms request dto
 */
@AllArgsConstructor
@Setter
@Getter
public class SmsRequestDto extends RequestDto {

    private MobileNoRequestDto recipientNo;
    private final String message;

    @Override
    public String toLogJson() {
        return toJson();
    }
}
