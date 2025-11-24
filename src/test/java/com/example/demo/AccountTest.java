package com.example.demo;
import com.example.demo.dto.CreateAccount;
import com.example.demo.dto.DepositWithdraw;
import com.example.demo.dto.Transfer;
import com.example.demo.exceptions.AccountNotFoundException;
import com.example.demo.exceptions.InsufficientBalanceException;
import com.example.demo.model.Account;

import com.example.demo.repo.AccountRepo;
import com.example.demo.repo.TransactionRepo;
import com.example.demo.services.AccountImp;
import com.example.demo.services.TransactionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.Instant;


import static org.mockito.Mockito.*;

class AccountTest {

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountImp accountImp;

    public AccountTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        CreateAccount req = new CreateAccount();
        req.setHolderName("Madiha");

        Account saved = Account.builder()
                .accountNumber("MAD1234")
                .holderName("Madiha")
                .balance(0.0)
                .status("ACTIVE")
                .createdAt(Instant.now())
                .build();

        when(accountRepo.save(any(Account.class))).thenReturn(saved);

        var response = accountImp.createAccount(req);

        Assertions.assertEquals("Madiha", response.getHolderName());
        Assertions.assertEquals(0.0, response.getBalance());
    }

    @Test
    void testGetAccount_NotFound() {
        when(accountRepo.findByAccountNumber("XYZ9999")).thenReturn(null);

        Assertions.assertThrows(AccountNotFoundException.class,
                () -> accountImp.getAccount("XYZ9999"));
    }

    @Test
    void testDeposit() {
        Account acc = Account.builder()
                .accountNumber("MAD1234")
                .holderName("Madiha")
                .balance(1000.0)
                .status("ACTIVE")
                .createdAt(Instant.now())
                .build();

        when(accountRepo.findByAccountNumber("MAD1234")).thenReturn(acc);
        when(accountRepo.save(any(Account.class))).thenReturn(acc);

        DepositWithdraw req = new DepositWithdraw();
        req.setAmount(500.0);

        var response = accountImp.deposit("MAD1234", req);

        Assertions.assertEquals(1500.0, response.getBalance());
    }

    @Test
    void testWithdraw_InsufficientBalance() {
        Account acc = Account.builder()
                .accountNumber("MAD1234")
                .holderName("Madiha")
                .balance(100.0)
                .status("ACTIVE")
                .build();

        when(accountRepo.findByAccountNumber("MAD1234")).thenReturn(acc);

        DepositWithdraw req = new DepositWithdraw();
        req.setAmount(500.0);

        Assertions.assertThrows(InsufficientBalanceException.class,
                () -> accountImp.withdraw("MAD1234", req));
    }

    @Test
    void testTransfer() {
        Account src = Account.builder()
                .accountNumber("SRC001")
                .holderName("Madiha")
                .balance(2000.0)
                .status("ACTIVE").build();

        Account dest = Account.builder()
                .accountNumber("DEST001")
                .holderName("Shaik")
                .balance(1000.0)
                .status("ACTIVE").build();

        when(accountRepo.findByAccountNumber("SRC001")).thenReturn(src);
        when(accountRepo.findByAccountNumber("DEST001")).thenReturn(dest);

        Transfer req = new Transfer();
        req.setSourceAccount("SRC001");
        req.setDestinationAccount("DEST001");
        req.setAmount(500.0);

        String msg = accountImp.transfer(req);

        Assertions.assertEquals("Transfer successful", msg);
        Assertions.assertEquals(1500.0, src.getBalance());
        Assertions.assertEquals(1500.0, dest.getBalance());
    }
}

