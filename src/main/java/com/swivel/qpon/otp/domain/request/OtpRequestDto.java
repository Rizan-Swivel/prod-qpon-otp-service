package com.swivel.qpon.otp.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OtpRequestDto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequestDto extends RequestDto {

    private MobileNoRequestDto mobileNo;
    private String email;

    /**
     * This method checks required fields are available.
     *
     * @return true/ false
     */
    @Override
    public boolean isRequiredAvailable() {
        if (mobileNo != null) {
            return mobileNo.isRequiredAvailable();
        } else {
            return isNonEmpty(email);
        }
    }

    @Override
    public String toLogJson() {
        return toJson();
    }
}
