package com.example.paymentsapi.services;

import com.example.paymentsapi.model.DecimalNumber;
import com.example.paymentsapi.model.Payment;
import com.example.paymentsapi.model.PaymentStatus;
import com.example.paymentsapi.repositories.PaymentRepository;
import com.example.paymentsapi.utils.PaymentNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component("paymentService")
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Optional<Payment> findById(UUID id) {
        return paymentRepository.findById(id);
    }

    public Payment[] getAll(Pageable pageable) {
        Page<Payment> page = paymentRepository.findAll(pageable);
        return page.stream().toArray(p -> new Payment[page.getTotalPages()]);
    }

    public Payment create(DecimalNumber amount, String clientName) {
        Payment payment = new Payment(
                UUID.randomUUID(),
                amount.amount(),
                amount.decimals(),
                PaymentStatus.Begin,
                clientName,
                new Date().getTime());
        paymentRepository.save(payment);
        return payment;
    }

    public Payment update(UUID id, PaymentStatus status) throws PaymentNotFoundException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        payment.setStatus(status);
        paymentRepository.save(payment);

        return payment;
    }

    public void delete(UUID id) {
        paymentRepository.deleteById(id);
    }
}
