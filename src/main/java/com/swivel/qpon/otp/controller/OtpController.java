package com.swivel.qpon.otp.controller;

import com.swivel.qpon.otp.configuration.Translator;
import com.swivel.qpon.otp.domain.request.OtpRequestDto;
import com.swivel.qpon.otp.domain.request.OtpVerifyRequestDto;
import com.swivel.qpon.otp.enums.ErrorResponseStatusType;
import com.swivel.qpon.otp.enums.SuccessResponseStatusType;
import com.swivel.qpon.otp.exception.InvalidOtpKeyException;
import com.swivel.qpon.otp.exception.OTPServiceException;
import com.swivel.qpon.otp.service.EmailOtpService;
import com.swivel.qpon.otp.service.SmsOtpService;
import com.swivel.qpon.otp.util.OtpGenerator;
import com.swivel.qpon.otp.util.Validator;
import com.swivel.qpon.otp.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OtpController
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/otp")
public class OtpController extends Controller {

    private final SmsOtpService smsOtpService;
    private final EmailOtpService emailOtpService;
    private final Validator validator;
    private final OtpGenerator otpGenerator;

    @Autowired
    public OtpController(SmsOtpService smsOtpService, EmailOtpService emailOtpService, Validator validator,
                         OtpGenerator otpGenerator, Translator translator) {
        super(translator);
        this.smsOtpService = smsOtpService;
        this.emailOtpService = emailOtpService;
        this.validator = validator;
        this.otpGenerator = otpGenerator;
    }

    /**
     * Generate the Otp and send via sms/email
     *
     * @param otpRequestDto otpRequestDto
     * @return ResponseEntity
     */
    @PostMapping(value = "/generate", consumes = APPLICATION_JSON,
            produces = APPLICATION_JSON)
    public ResponseEntity<ResponseWrapper> generateOtp(@RequestBody OtpRequestDto otpRequestDto) {
        try {
            if (otpRequestDto.getMobileNo() != null && otpRequestDto.getEmail() != null) {
                return getBadRequestError(ErrorResponseStatusType.MULTIPLE_TYPES_NOT_SUPPORTED);
            }
            if (otpRequestDto.isRequiredAvailable()) {
                log.debug("Inputs for generate otp : {} ", otpRequestDto.toLogJson());
                if (otpRequestDto.getMobileNo() != null) {
                    return generateSmsResponseEntity(otpRequestDto);
                } else {
                    return generateEmailResponseEntity(otpRequestDto);
                }
            } else {
                return getBadRequestError(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);
            }
        } catch (OTPServiceException e) {
            log.error("OTP generation was failed for: {} ", otpRequestDto.toLogJson(), e);
            return getInternalServerError();
        }

    }

    /**
     * Verify the Otp sent by user
     *
     * @param otpVerifyRequestDto otpVerifyRequestDto
     * @return ResponseEntity
     */
    @PostMapping(value = "/verify", consumes = APPLICATION_JSON,
            produces = APPLICATION_JSON)
    public ResponseEntity<ResponseWrapper> verifyOtp(@RequestBody OtpVerifyRequestDto otpVerifyRequestDto) {
        try {
            if (otpVerifyRequestDto.getMobileNo() != null && otpVerifyRequestDto.getEmail() != null) {
                return getBadRequestError(ErrorResponseStatusType.MULTIPLE_TYPES_NOT_SUPPORTED);
            }
            if (otpVerifyRequestDto.isRequiredAvailable()) {
                log.debug("Verify Otp inputs: {} ", otpVerifyRequestDto.toJson());
                if (otpVerifyRequestDto.getMobileNo() != null && otpVerifyRequestDto.getOtp() != null) {
                    return smsVerifyResponseEntity(otpVerifyRequestDto);
                } else {
                    return emailVerifyResponseEntity(otpVerifyRequestDto);
                }
            } else {
                return getBadRequestError(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);
            }
        } catch (OTPServiceException e) {
            log.error("OTP Verification was failed for: {} ", otpVerifyRequestDto.toLogJson(), e);
            return getInternalServerError();
        }

    }

    /**
     * Save the email and otp to cache and send email
     *
     * @param otpRequestDto otp request parameters
     * @return ResponseEntity
     */
    private ResponseEntity<ResponseWrapper> generateEmailResponseEntity(OtpRequestDto otpRequestDto) {
        if (validator.isValidEmail(otpRequestDto.getEmail().trim())) {
            int otp = otpGenerator.generateOtp();
            emailOtpService.sendOtp(otpRequestDto.getEmail(), otp);
            log.debug("Sent otp: {} for email: {}", otp, otpRequestDto.getEmail());
            return getSuccessResponse(SuccessResponseStatusType.SENT_OTP);
        } else {
            return getBadRequestError(ErrorResponseStatusType.INVALID_EMAIL);
        }
    }

    /**
     * Save the mobileNo to cache and send sms
     *
     * @return ResponseEntity
     */
    private ResponseEntity<ResponseWrapper> generateSmsResponseEntity(OtpRequestDto otpRequestDto) {
        if (validator.isValidMobileNo(otpRequestDto.getMobileNo().getNo())) {
            String mobileNo = otpRequestDto.getMobileNo().getNo().replace("-", "");
            int otp = otpGenerator.generateOtp();
            smsOtpService.sendOtp(otpRequestDto.getMobileNo(), mobileNo, otp);
            log.debug("Sent otp: {} for mobile: {}", otp, mobileNo);
            return getSuccessResponse(SuccessResponseStatusType.SENT_OTP);
        } else {
            return getBadRequestError(ErrorResponseStatusType.INVALID_MOBILE_NO);
        }
    }

    /**
     * Read the cache and get the saved otp for email
     *
     * @param otpVerifyRequestDto otpVerifyRequestDto
     * @return ResponseEntity
     */
    private ResponseEntity<ResponseWrapper> emailVerifyResponseEntity(OtpVerifyRequestDto otpVerifyRequestDto) {
        if (validator.isValidEmail(otpVerifyRequestDto.getEmail())) {
            if (validator.isValidOtp(otpVerifyRequestDto.getOtp())) {
                return verifyEmailOtp(otpVerifyRequestDto);
            } else {
                return getBadRequestError(ErrorResponseStatusType.INVALID_OTP);
            }
        } else {
            return getBadRequestError(ErrorResponseStatusType.INVALID_EMAIL);
        }
    }

    /**
     * Call to emailOtpService and verify the otp
     *
     * @param otpVerifyRequestDto otpVerifyRequestDto
     * @return ResponseEntity
     */
    private ResponseEntity<ResponseWrapper> verifyEmailOtp(OtpVerifyRequestDto otpVerifyRequestDto) {
        try {
            if (emailOtpService.verifyOtp(otpVerifyRequestDto.getEmail(), otpVerifyRequestDto.getOtp())) {
                return getSuccessResponse(SuccessResponseStatusType.VERIFIED_OTP);
            } else {
                return getBadRequestError(ErrorResponseStatusType.INVALID_OTP);
            }
        } catch (InvalidOtpKeyException e) {
            log.error("Invalid mobile number or expired otp {} ", otpVerifyRequestDto.toLogJson(), e);
            return getBadRequestError(ErrorResponseStatusType.INVALID_EMAIL_OR_EXPIRED_OTP);
        }
    }

    /**
     * Read the cache and get the saved otp for sms
     *
     * @param otpVerifyRequestDto otpVerifyRequestDto
     * @return ResponseEntity
     */
    private ResponseEntity<ResponseWrapper> smsVerifyResponseEntity(OtpVerifyRequestDto otpVerifyRequestDto) {
        if (validator.isValidMobileNo(otpVerifyRequestDto.getMobileNo().getNo())) {
            if (validator.isValidOtp(otpVerifyRequestDto.getOtp())) {
                return verifySmsOtp(otpVerifyRequestDto);
            } else {
                return getBadRequestError(ErrorResponseStatusType.INVALID_OTP);
            }
        } else {
            return getBadRequestError(ErrorResponseStatusType.INVALID_MOBILE_NO);
        }
    }

    /**
     * Call to smsOtpService and verify the otp
     *
     * @param otpVerifyRequestDto otpVerifyRequestDto
     * @return ResponseEntity
     */
    private ResponseEntity<ResponseWrapper> verifySmsOtp(OtpVerifyRequestDto otpVerifyRequestDto) {
        try {
            String mobileNo = otpVerifyRequestDto.getMobileNo().getNo().replace("-", "");
            if (smsOtpService.verifyOtp(mobileNo, otpVerifyRequestDto.getOtp())) {
                return getSuccessResponse(SuccessResponseStatusType.VERIFIED_OTP);
            } else {
                return getBadRequestError(ErrorResponseStatusType.INVALID_OTP);
            }
        } catch (InvalidOtpKeyException e) {
            log.error("Invalid mobile number or expired otp {} ", otpVerifyRequestDto.toLogJson(), e);
            return getBadRequestError(ErrorResponseStatusType.INVALID_MOBILE_NUMBER_OR_EXPIRED_OTP);
        }
    }

}
