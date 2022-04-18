package com.swivel.qpon.otp.service;

import com.swivel.qpon.otp.configuration.Translator;
import com.swivel.qpon.otp.domain.request.MailRequestDto;
import com.swivel.qpon.otp.domain.request.MobileNoRequestDto;
import com.swivel.qpon.otp.domain.request.SmsRequestDto;
import com.swivel.qpon.otp.enums.NotificationTemplateType;
import com.swivel.qpon.otp.exception.OTPServiceException;
import com.swivel.qpon.otp.wrapper.SendEmailWrapper;
import com.swivel.qpon.otp.wrapper.SendSmsWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
public class NotificationService {

    private final String appKeyHeader;
    private final String timeZoneHeader;
    private final String timeZoneValue;
    private final String utilServiceAppKey;
    private final String mailSendUri;
    private final String smsSendUri;
    private final RestTemplate restTemplate;
    private final Translator translator;


    @Autowired
    public NotificationService(@Value("${header.appKey.header}") String appKeyHeader,
                               @Value("${header.timeZone.header}") String timeZoneHeader,
                               @Value("${header.timeZone.value}") String timeZoneValue,
                               @Value("${util.baseUrl}") String baseUrl,
                               @Value("${util.uri.sendMail}") String mailPath,
                               @Value("${util.uri.sendSms}") String smsPath,
                               @Value("${util.utilServiceAppKey}") String appKey,
                               RestTemplate restTemplate, Translator translator) {
        this.appKeyHeader = appKeyHeader;
        this.timeZoneHeader = timeZoneHeader;
        this.timeZoneValue = timeZoneValue;
        this.translator = translator;
        this.mailSendUri = baseUrl + mailPath;
        this.smsSendUri = baseUrl + smsPath;
        this.utilServiceAppKey = appKey;
        this.restTemplate = restTemplate;
    }

    /**
     * This method send email by adding the OTP code
     *
     * @param receiver receiver email address
     * @param otp      otp code
     */
    public void sendMail(String receiver, String otp) {
        var headers = getAuthHeaders();
        var mailRequestDto = new MailRequestDto(receiver,
                translator.toLocale(NotificationTemplateType.SEND_OTP.getEmailHeader()),
                translator.toLocale(NotificationTemplateType.SEND_OTP.getEmailBody()).replace("<OTP>", otp));
        HttpEntity<MailRequestDto> entity = new HttpEntity<>(mailRequestDto, headers);
        try {
            log.debug("Calling util service to send the email to: {}", receiver);
            restTemplate.exchange(mailSendUri, HttpMethod.POST, entity, SendEmailWrapper.class);
            log.debug("Sending email was success to: {}", receiver);
        } catch (HttpClientErrorException e) {
            log.error("Sending email was failed to: {}", receiver, e);
            throw new OTPServiceException(e.getMessage(), e);
        }
    }

    /**
     * This method send sms by adding the otp code
     *
     * @param recipientNo recipientNo
     * @param otp         otp
     */
    public void sendSms(MobileNoRequestDto recipientNo, String otp) {
        var headers = getAuthHeaders();
        var smsRequestDto = new SmsRequestDto(recipientNo, translator.
                toLocale(NotificationTemplateType.SEND_OTP.getSms()).replace("<OTP>", otp));
        HttpEntity<SmsRequestDto> entity = new HttpEntity<>(smsRequestDto, headers);
        try {
            log.debug("Calling util service to send the sms to: {}", recipientNo.toLogJson());
            restTemplate.exchange(smsSendUri, HttpMethod.POST, entity, SendSmsWrapper.class);
            log.debug("Sending sms was success to: {}", recipientNo.toLogJson());
        } catch (HttpClientErrorException e) {
            log.error("Sending sms was failed to: {}", recipientNo.toLogJson(), e);
            throw new OTPServiceException(e.getMessage(), e);
        }
    }

    /**
     * This method returns headers for util service urls.
     */
    private HttpHeaders getAuthHeaders() {
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(timeZoneHeader, timeZoneValue);
        headers.set(appKeyHeader, utilServiceAppKey);
        return headers;
    }
}
