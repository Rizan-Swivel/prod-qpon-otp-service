package com.swivel.qpon.otp.service;

import com.swivel.qpon.otp.repository.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Otp service class for Email sending and verification
 */
@Service
@Slf4j
public class EmailOtpService implements OtpService {

    private final CacheRepository cacheRepository;
    private final NotificationService notificationService;

    public EmailOtpService(CacheRepository cacheRepository, NotificationService notificationService) {
        this.cacheRepository = cacheRepository;
        this.notificationService = notificationService;
    }

    /**
     * Save otp to cache with key email and value otp and send email
     *
     * @param email value
     * @param otp   otp
     */
    public void sendOtp(String email, int otp) {
        cacheRepository.put(email, otp);
        log.debug("Sending OTP: {} to email: {}", otp, email);
        notificationService.sendMail(email, String.valueOf(otp));
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
