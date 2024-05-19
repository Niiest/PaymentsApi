package com.example.paymentsapi.utils;

public class InvalidPaymentOperationException extends RuntimeException {
    public InvalidPaymentOperationException(String message) {
        super(message);
    }
}
