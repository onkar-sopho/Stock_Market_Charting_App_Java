package com.onkar.stockMarket.exceptions;

public class StockMarketException extends RuntimeException {
    public StockMarketException(String exceptionMessage, Exception e) {
        super(exceptionMessage, e);
    }
    public StockMarketException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
