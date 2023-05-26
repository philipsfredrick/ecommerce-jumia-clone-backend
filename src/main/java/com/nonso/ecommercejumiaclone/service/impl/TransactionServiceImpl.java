package com.nonso.ecommercejumiaclone.service.impl;

import com.nonso.ecommercejumiaclone.entities.Transaction;
import com.nonso.ecommercejumiaclone.repository.TransactionRepository;
import com.nonso.ecommercejumiaclone.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    @Override
    public List<Transaction> getVendorTransactionRecords() {
        return transactionRepository.findAll();
    }
}
