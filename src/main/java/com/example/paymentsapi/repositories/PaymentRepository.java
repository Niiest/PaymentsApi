package com.example.paymentsapi.repositories;

import com.example.paymentsapi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository  extends JpaRepository<Payment, UUID> {
}
