package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repo.TransactionRepo;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepo transactionRepository;

    @GetMapping("/getAllTransactions")
    public Object getAllTransactions() {
        return transactionRepository.findAll();
    }
}

