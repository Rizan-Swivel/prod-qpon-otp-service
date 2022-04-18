package com.swivel.qpon.otp.service;

import com.swivel.qpon.otp.domain.request.MobileNoRequestDto;
import com.swivel.qpon.otp.exception.InvalidOtpKeyException;
import com.swivel.qpon.otp.repository.CacheRepository;

import java.util.Optional;

/**
 * Otp service
 */
public interface OtpService {

    /**
     * Verify the Otp this returns true if otp is valid else returns false
     *
     * @param cacheRepository cacheRepository
     * @param key             mobile/email
     * @param value           otp
     * @return true/false
     */
    default boolean verifyOtp(CacheRepository cacheRepository, String key, String value) {

        Optional<String> cachedOtp = cacheRepository.get(key);

        if (cachedOtp.isPresent()) {
            if (cachedOtp.get().equals(value)) {
                cacheRepository.remove(key);
                return true;
            }
        } else {
            throw new InvalidOtpKeyException("Invalid otp key ");
        }
        return false;
    }

    /**
     * Verify the Otp this returns true if otp is valid else returns false
     *
     * @param key   mobileNo/email
     * @param value otp
     * @return false
     */
    boolean verifyOtp(String key, String value);

}
