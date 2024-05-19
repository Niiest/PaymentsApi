package com.example.paymentsapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String clientName;
    private long balanceAmount;
    private int balanceDecimals;
    private long createdAt;

    public Account(String clientName, long balanceAmount, int balanceDecimals, long createdAt) {
        this.clientName = clientName;
        this.balanceAmount = balanceAmount;
        this.balanceDecimals = balanceDecimals;
        this.createdAt = createdAt;
    }

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public long getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(long balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public int getBalanceDecimals() {
        return balanceDecimals;
    }

    public void setBalanceDecimals(int balanceDecimals) {
        this.balanceDecimals = balanceDecimals;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    // Methods
    public double calculateBalance() {
        return this.balanceAmount + ((double)this.balanceDecimals) / 100.0;
    }

}
