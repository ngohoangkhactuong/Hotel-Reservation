package com.khactuong.hotel.common.enumeration;

public enum ERoomType {

    Single("Single"),
    Double("Double"),
    Twin("Twin"),
    Triple("Triple"),
    Queen("Queen"),
    King("King"),
    Suite("Suite");

    private final String NAME;

    ERoomType(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
