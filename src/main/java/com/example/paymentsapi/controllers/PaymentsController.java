package com.example.paymentsapi.controllers;

import com.example.paymentsapi.http.requests.CreatePaymentRequest;
import com.example.paymentsapi.http.requests.UpdatePaymentRequest;
import com.example.paymentsapi.model.*;
import com.example.paymentsapi.services.PaymentService;
import com.example.paymentsapi.utils.PaymentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    @Operation(summary = "List payments")
    public Payment[] listPayments(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by("createdAt").descending().and(Sort.by("clientName")));
        return paymentService.getAll(pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a payment by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the payment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Payment.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content) })
    public Payment getPaymentById(@PathVariable UUID id) throws PaymentNotFoundException {
        return paymentService.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    @PostMapping
    @Operation(summary = "Create a payment")
    public Payment createPayment(@RequestBody final CreatePaymentRequest request) {
        return paymentService.create(request.amount(), request.clientName());
    }

    @PutMapping("{id}")
    @Operation(summary = "Update the payment")
    public Payment updatePayment(@PathVariable UUID id, @RequestBody final UpdatePaymentRequest request) {
        return paymentService.update(id, request.status());
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete the payment")
    public void updatePayment(@PathVariable UUID id) {
        paymentService.delete(id);
    }
}