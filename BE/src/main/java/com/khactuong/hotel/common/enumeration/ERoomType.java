package com.khactuong.hotel.common.enumeration;

public enum ERoomType {

    Single("Single"),
    Standard("Standard"),
    Double("Double"),
    Twin("Twin"),
    Triple("Triple"),
    Queen("Queen"),
    King("King"),
    Suite("Suite"),
    Deluxe("Deluxe"),
    Classic("Classic"),
    Triple_Classic("Triple Classic"),
    Business_Class("Business Class"),
    Royal_Class("Royal Class"),
    Superior_Ocean("Superior Ocean"),
    Economy_Classic("Economy Classic");          
    
    private final String NAME;

    ERoomType(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
