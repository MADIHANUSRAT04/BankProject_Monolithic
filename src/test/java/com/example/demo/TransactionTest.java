package com.example.demo;

import com.example.demo.model.Transaction;
import com.example.demo.repo.TransactionRepo;
import com.example.demo.services.TransactionImp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class TransactionTest {

    @Mock
    private TransactionRepo transactionRepo;

    @InjectMocks
    private TransactionImp transactionImp;

    public TransactionTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRecordDeposit() {
        Transaction t = new Transaction();
        t.setAmount(500.0);
        t.setType("DEPOSIT");

        when(transactionRepo.save(any(Transaction.class))).thenReturn(t);

        Transaction saved = transactionImp.recordDeposit("MAD1234", 500.0);

        Assertions.assertEquals("DEPOSIT", saved.getType());
        Assertions.assertEquals(500.0, saved.getAmount());
    }

    @Test
    void testRecordWithdraw() {
        Transaction t = new Transaction();
        t.setAmount(300.0);
        t.setType("WITHDRAW");

        when(transactionRepo.save(any(Transaction.class))).thenReturn(t);

        Transaction saved = transactionImp.recordWithdraw("MAD1234", 300.0);

        Assertions.assertEquals("WITHDRAW", saved.getType());
        Assertions.assertEquals(300.0, saved.getAmount());
    }

    @Test
    void testRecordTransfer() {
        Transaction t = new Transaction();
        t.setAmount(200.0);
        t.setType("TRANSFER");

        when(transactionRepo.save(any(Transaction.class))).thenReturn(t);

        Transaction saved = transactionImp.recordTransfer("SRC001", "DEST001", 200.0);

        Assertions.assertEquals("TRANSFER", saved.getType());
        Assertions.assertEquals(200.0, saved.getAmount());
    }
}
