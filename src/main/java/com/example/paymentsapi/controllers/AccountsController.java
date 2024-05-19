package com.example.paymentsapi.controllers;

import com.example.paymentsapi.http.requests.CreateAccountRequest;
import com.example.paymentsapi.http.requests.CreatePaymentRequest;
import com.example.paymentsapi.http.requests.UpdateAccountRequest;
import com.example.paymentsapi.http.requests.UpdatePaymentRequest;
import com.example.paymentsapi.model.Account;
import com.example.paymentsapi.model.Payment;
import com.example.paymentsapi.services.AccountService;
import com.example.paymentsapi.services.PaymentService;
import com.example.paymentsapi.utils.AccountNotFoundException;
import com.example.paymentsapi.utils.PaymentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    @Operation(summary = "List accounts")
    public Account[] listAccounts(
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        Pageable pageable = PageRequest.of(
                pageNumber - 1,
                pageSize,
                Sort.by("clientName").ascending());
        return accountService.getAll(pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get an account by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the account",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Payment.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content) })
    public Account getAccountById(@PathVariable int id) throws AccountNotFoundException {
        return accountService.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @PostMapping
    @Operation(summary = "Create an account")
    public Account createAccount(@Valid @RequestBody final CreateAccountRequest request) {
        return accountService.create(request.clientName());
    }

    @PutMapping("{id}")
    @Operation(summary = "Update the account")
    public Account updateAccount(@Valid @PathVariable int id, @RequestBody final UpdateAccountRequest request) {
        return accountService.update(id, request.clientName());
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete the payment")
    public void deleteAccount(@PathVariable int id) {
        accountService.delete(id);
    }
}