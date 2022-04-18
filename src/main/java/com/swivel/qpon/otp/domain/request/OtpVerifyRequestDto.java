package com.swivel.qpon.otp.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OtpVerifyRequestDto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerifyRequestDto extends RequestDto {

    private MobileNoRequestDto mobileNo;
    private String email;
    private String otp;


    /**
     * This method checks required fields are available.
     *
     * @return true/ false
     */
    @Override
    public boolean isRequiredAvailable() {
        if (mobileNo != null) {
            return mobileNo.isRequiredAvailable() && isNonEmpty(otp);
        } else {
            return isNonEmpty(email) && isNonEmpty(otp);
        }
    }

    @Override
    public String toLogJson() {
        return toJson();
    }
}
