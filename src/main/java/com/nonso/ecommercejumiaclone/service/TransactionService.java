package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.entities.Transaction;
import com.nonso.ecommercejumiaclone.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> getVendorTransactionRecords() {
        return transactionRepository.findAll();
    }
}
