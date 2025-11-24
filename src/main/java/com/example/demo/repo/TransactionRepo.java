package com.example.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.model.Transaction;

import java.util.List;

public interface TransactionRepo extends MongoRepository<Transaction, String> {

    List<Transaction> findBySourceAccount(String accountNumber);

    List<Transaction> findByDestinationAccount(String accountNumber);
}
