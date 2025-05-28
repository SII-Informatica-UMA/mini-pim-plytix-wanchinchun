package com.uma.wanchinchun.exceptions;

public class ExceedsLimitException extends RuntimeException {
    public ExceedsLimitException(String message) {
        super(message);
    }
}
