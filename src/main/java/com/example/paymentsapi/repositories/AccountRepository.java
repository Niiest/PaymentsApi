package com.example.paymentsapi.repositories;

import com.example.paymentsapi.model.Account;
import com.example.paymentsapi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface AccountRepository extends JpaRepository<Account, Integer> {
}
