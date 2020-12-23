package com.onkar.stockMarket.exceptions;

public class StockExchangeNotFoundException extends RuntimeException {
    public StockExchangeNotFoundException(String message) {
        super(message);
    }
}
