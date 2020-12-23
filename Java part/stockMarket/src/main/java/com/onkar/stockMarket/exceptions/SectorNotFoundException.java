package com.onkar.stockMarket.exceptions;

public class SectorNotFoundException extends RuntimeException {
    public SectorNotFoundException(String message) {
        super(message);
    }
}
