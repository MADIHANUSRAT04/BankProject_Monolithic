package com.example.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.demo.model.Transaction;
import com.example.demo.repo.TransactionRepo;

import java.time.Instant;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionImp implements TransactionService {

	private final TransactionRepo transactionRepository;

    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public Transaction recordDeposit(String accountNumber, Double amount) {

        log.info("Recording DEPOSIT of {} into account {}", amount, accountNumber);

        Transaction t = Transaction.builder()
                .transactionId(generateTransactionId())
                .type("DEPOSIT")
                .amount(amount)
                .timestamp(Instant.now())
                .status("SUCCESS")
                .sourceAccount(null)
                .destinationAccount(accountNumber)
                .build();

        Transaction saved = transactionRepository.save(t);

        log.info("Deposit transaction saved: {}", saved.getTransactionId());

        return saved;
    }

    @Override
    public Transaction recordWithdraw(String accountNumber, Double amount) {

        log.info("Recording WITHDRAW of {} from account {}", amount, accountNumber);

        Transaction t = Transaction.builder()
                .transactionId(generateTransactionId())
                .type("WITHDRAW")
                .amount(amount)
                .timestamp(Instant.now())
                .status("SUCCESS")
                .sourceAccount(accountNumber)
                .destinationAccount(null)
                .build();

        Transaction saved = transactionRepository.save(t);

        log.info("Withdraw transaction saved: {}", saved.getTransactionId());

        return saved;
    }

    @Override
    public Transaction recordTransfer(String source, String destination, Double amount) {

        log.info("Recording TRANSFER of {} from {} to {}", amount, source, destination);

        Transaction t = Transaction.builder()
                .transactionId(generateTransactionId())
                .type("TRANSFER")
                .amount(amount)
                .timestamp(Instant.now())
                .status("SUCCESS")
                .sourceAccount(source)
                .destinationAccount(destination)
                .build();

        Transaction saved = transactionRepository.save(t);

        log.info("Transfer transaction saved: {}", saved.getTransactionId());

        return saved;
    }
}