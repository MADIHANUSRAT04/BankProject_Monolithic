package com.example.demo.services;

import com.example.demo.model.Transaction;
public interface TransactionService {

    Transaction recordDeposit(String accountNumber, Double amount);

    Transaction recordWithdraw(String accountNumber, Double amount);

    Transaction recordTransfer(String source, String destination, Double amount);
}
