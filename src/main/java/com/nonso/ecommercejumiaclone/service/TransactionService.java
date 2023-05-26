package com.nonso.ecommercejumiaclone.service;

import com.nonso.ecommercejumiaclone.entities.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getVendorTransactionRecords();
}
