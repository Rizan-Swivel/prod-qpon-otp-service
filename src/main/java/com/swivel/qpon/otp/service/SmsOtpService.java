package com.swivel.qpon.otp.service;

import com.swivel.qpon.otp.domain.request.MobileNoRequestDto;
import com.swivel.qpon.otp.repository.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Otp service class for SMS sending and verification
 */
@Service
@Slf4j
public class SmsOtpService implements OtpService {

    private final CacheRepository cacheRepository;
    private final NotificationService notificationService;

    @Autowired
    public SmsOtpService(CacheRepository cacheRepository, NotificationService notificationService) {
        this.cacheRepository = cacheRepository;
        this.notificationService = notificationService;
    }

    /**
     * Save otp to cache with key mobileNo and value otp and send sms
     *
     * @param mobileNoRequestDto mobileNoRequestDto
     * @param otp      otp
     */
    public void sendOtp(MobileNoRequestDto mobileNoRequestDto, String mobileNo, int otp) {
        cacheRepository.put(mobileNo, otp);
        log.debug("Sending OTP: {} to mobileNo: {}", otp, mobileNoRequestDto.getNo());
        notificationService.sendSms(mobileNoRequestDto, String.valueOf(otp));
    }

    /**
     * Verify the opt value provided from cache and remove from the cache
     *
     * @param key   key
     * @param value value
     * @return true/false
     */
    @Override
    public boolean verifyOtp(String key, String value) {
        return verifyOtp(cacheRepository, key, value);
    }

}