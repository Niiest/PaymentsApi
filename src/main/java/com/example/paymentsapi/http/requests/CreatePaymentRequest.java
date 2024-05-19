package com.example.paymentsapi.http.requests;

import com.example.paymentsapi.model.DecimalNumber;
import com.example.paymentsapi.model.PaymentType;

public record CreatePaymentRequest(int accountId, DecimalNumber amount, PaymentType type) { }
