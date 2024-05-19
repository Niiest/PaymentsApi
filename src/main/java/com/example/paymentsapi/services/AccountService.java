package com.example.paymentsapi.services;

import com.example.paymentsapi.model.Account;
import com.example.paymentsapi.repositories.AccountRepository;
import com.example.paymentsapi.utils.AccountNotFoundException;
import com.example.paymentsapi.utils.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component("accountService")
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> findById(int id) {
        return accountRepository.findById(id);
    }

    public Account[] getAll(Pageable pageable) {
        Page<Account> page = accountRepository.findAll(pageable);
        return page.stream().toArray(p -> new Account[page.getNumberOfElements()]);
    }

    public Account create(String clientName) throws BadRequestException {
        if (clientName == null || clientName.isEmpty()) {
            throw new BadRequestException("clientName must not be empty");
        }

        Account account = new Account(
                clientName,
                0,
                0,
                new Date().getTime());

        accountRepository.save(account);

        return account;
    }

    public Account update(int id, String clientName) throws AccountNotFoundException, BadRequestException {
        if (clientName == null || clientName.isEmpty()) {
            throw new BadRequestException("clientName must not be empty");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        account.setClientName(clientName);

        accountRepository.save(account);

        return account;
    }

    public void delete(int id) {
        accountRepository.deleteById(id);
    }
}
