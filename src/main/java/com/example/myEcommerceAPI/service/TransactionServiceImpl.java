package com.example.myEcommerceAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.myEcommerceAPI.entity.Transaction;
import com.example.myEcommerceAPI.repository.TransactionRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    
   @Override
   public Optional<Transaction> GetTransaction(String id) {
       // TODO Auto-generated method stub
       return Optional.empty();
   }
   @Override
   public Transaction SaveTransaction(Transaction Transaction) {
       // TODO Auto-generated method stub
       return null;
   }
   @Override
   public void DeleteTransaction(String id) {
       // TODO Auto-generated method stub
       
   }
   @Override
   public List<Transaction> GetTransactions() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'GetTransactions'");
   }
}
