package com.nonso.ecommercejumiaclone.controllers;

import com.nonso.ecommercejumiaclone.entities.Transaction;
import com.nonso.ecommercejumiaclone.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactionRecords() {
        return new ResponseEntity<>(transactionService.getVendorTransactionRecords(), OK);
    }
}
