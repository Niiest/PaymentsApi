package com.example.paymentsapi.model;

public record DecimalNumber(long amount, int decimals) {
    public double asDouble() {
        return amount + ((double)decimals) / 100.0;
    }
}
