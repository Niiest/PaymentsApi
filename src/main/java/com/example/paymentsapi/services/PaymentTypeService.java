package com.example.paymentsapi.services;

import com.example.paymentsapi.model.DecimalNumber;
import com.example.paymentsapi.model.PaymentType;

public final class PaymentTypeService {
    public static double calculateAmount(PaymentType paymentType, DecimalNumber amount) {
        return amount.asDouble() * (paymentType == PaymentType.CashIn ? 1 : -1);
    }

    public static long calculateForNumber(PaymentType paymentType, long number) {
        return number * (paymentType == PaymentType.CashIn ? 1 : -1);
    }
}
