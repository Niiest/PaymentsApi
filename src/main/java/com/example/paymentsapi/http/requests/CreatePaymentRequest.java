package com.example.paymentsapi.http.requests;

import com.example.paymentsapi.model.DecimalNumber;

public record CreatePaymentRequest(String clientName, DecimalNumber amount) { }
