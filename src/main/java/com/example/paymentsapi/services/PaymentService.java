package com.example.paymentsapi.services;

import com.example.paymentsapi.model.*;
import com.example.paymentsapi.repositories.AccountRepository;
import com.example.paymentsapi.repositories.PaymentRepository;
import com.example.paymentsapi.utils.AccountNotFoundException;
import com.example.paymentsapi.utils.InvalidPaymentOperationException;
import com.example.paymentsapi.utils.InvalidPaymentStateException;
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
    @Autowired
    private AccountRepository accountRepository;

    public Optional<Payment> findById(UUID id) {
        return paymentRepository.findById(id);
    }

    public Payment[] getAll(Pageable pageable) {
        Page<Payment> page = paymentRepository.findAll(pageable);
        return page.stream().toArray(p -> new Payment[page.getNumberOfElements()]);
    }

    public Payment create(DecimalNumber amount, int accountId, PaymentType type) throws InvalidPaymentOperationException, AccountNotFoundException {
        if (amount.decimals() < 0 || amount.decimals() > 100) {
            throw new InvalidPaymentOperationException("Decimals must be in range of 0 to 100");
        }

        if (amount.asDouble() <= 0) {
            throw new InvalidPaymentOperationException("Amount must be greater than zero");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        if (account.calculateBalance() + PaymentTypeService.calculateAmount(type, amount) < 0) {
            throw new InvalidPaymentOperationException("Operation not permitted due to negative amount");
        }

        Payment payment = new Payment(
                UUID.randomUUID(),
                amount.amount(),
                amount.decimals(),
                PaymentStatus.Begin,
                type,
                accountId,
                new Date().getTime());

        paymentRepository.save(payment);

        return payment;
    }

    public Payment cancel(UUID id) throws InvalidPaymentStateException, PaymentNotFoundException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        if (payment.getStatus() == PaymentStatus.Cancelled) {
            throw new InvalidPaymentStateException("Payment cancelled already");
        }

        if (payment.getStatus() == PaymentStatus.Committed) {
            throw new InvalidPaymentStateException("Payment committed already");
        }

        payment.setStatus(PaymentStatus.Cancelled);

        paymentRepository.save(payment);

        return payment;
    }

    public Payment commit(UUID id) throws InvalidPaymentStateException, PaymentNotFoundException, AccountNotFoundException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        int accountId = payment.getAccountId();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        if (payment.getStatus() == PaymentStatus.Cancelled) {
            throw new InvalidPaymentStateException("Payment cancelled already");
        }

        if (payment.getStatus() == PaymentStatus.Committed) {
            throw new InvalidPaymentStateException("Payment committed already");
        }

        payment.setStatus(PaymentStatus.Committed);

        int decimals = account.getBalanceDecimals() + (int)PaymentTypeService.calculateForNumber(payment.getType(), payment.getDecimals());
        account.setBalanceDecimals(decimals < 0 ? 100 + decimals : 100 - decimals);

        int amountOverdue = decimals < 0
                ? -1
                : decimals >= 100
                    ? 1
                    : 0;

        account.setBalanceAmount(
                account.getBalanceAmount() +
                PaymentTypeService.calculateForNumber(payment.getType(), payment.getAmount()) +
                amountOverdue);

        paymentRepository.save(payment);
        accountRepository.save(account);

        return payment;
    }

    public void delete(UUID id) {
        paymentRepository.deleteById(id);
    }
}
