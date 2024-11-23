package com.khactuong.hotel.common.enumeration;

public enum ERole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String NAME;

    ERole(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
