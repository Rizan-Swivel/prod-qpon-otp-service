package com.swivel.qpon.otp.enums;

import lombok.Getter;

@Getter
public enum NotificationTemplateType {
    SEND_OTP("SMS-BODY", "EMAIL-HEADER", "EMAIL-BODY");

    private final String sms;
    private final String emailHeader;
    private final String emailBody;

    NotificationTemplateType(String sms, String emailHeader, String emailBody) {
        this.sms = sms;
        this.emailHeader = emailHeader;
        this.emailBody = emailBody;
    }
}
