package com.example.paymentsapi.http.requests;

import com.example.paymentsapi.model.PaymentStatus;

public record UpdatePaymentRequest(PaymentStatus status) { }
