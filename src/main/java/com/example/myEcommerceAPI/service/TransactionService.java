package com.example.myEcommerceAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.myEcommerceAPI.entity.Transaction;

@Service
public interface TransactionService {
    Transaction SaveTransaction(Transaction Transaction);
    Optional<Transaction> GetTransaction(String id);
    void DeleteTransaction(String id);
    List<Transaction> GetTransactions();
}
