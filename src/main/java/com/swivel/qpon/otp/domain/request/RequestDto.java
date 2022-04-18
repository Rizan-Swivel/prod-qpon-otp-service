package com.swivel.qpon.otp.domain.request;

import com.swivel.qpon.otp.domain.BaseDto;

/**
 * RequestDto - All RequestDto classes extends this class
 */
public abstract class RequestDto implements BaseDto {

    /**
     * This method checks all required fields are available for a request.
     *
     * @return true/ false
     */
    public boolean isRequiredAvailable() {
        return true;
    }

    /**
     * This method checks the given field is non empty.
     *
     * @param field field
     * @return true/ false
     */
    protected boolean isNonEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }

}
