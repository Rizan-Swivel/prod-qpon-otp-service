package com.swivel.qpon.otp.enums;

/**
 * Enum values for response
 */
public enum ResponseStatusType {

    SUCCESS("Success"),
    ERROR("Error");

    private final String status;

    ResponseStatusType(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
