package com.example.paymentsapi.utils;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(int id) {
        super(String.format("Account %s not found", id));
    }
}
