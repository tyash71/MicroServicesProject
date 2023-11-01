package com.lcwy.hotel.exceptions;

public class ResouceNotFoundException extends RuntimeException {
    public ResouceNotFoundException(String s) {
        super(s);
    }

    public ResouceNotFoundException(){
        super("Resouce not found");
    }
}
