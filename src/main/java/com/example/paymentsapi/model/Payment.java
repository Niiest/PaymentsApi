package com.example.paymentsapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Payment {
    @Id
    private UUID id;
    private long amount;
    private int decimals;
    private PaymentStatus status;
    private PaymentType type;
    private int accountId;
    private long createdAt;

    public Payment() {
    }

    public Payment(UUID id, long amount, int decimals, PaymentStatus status, PaymentType type, int accountId, long createdAt) {
        this.id = id;
        this.amount = amount;
        this.decimals = decimals;
        this.status = status;
        this.type = type;
        this.accountId = accountId;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long created_at) {
        this.createdAt = created_at;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }
}