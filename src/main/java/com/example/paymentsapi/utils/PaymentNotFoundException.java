package com.example.paymentsapi.utils;

import java.util.UUID;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(UUID id) {
        super(String.format("Payment %s not found", id));
    }
}
