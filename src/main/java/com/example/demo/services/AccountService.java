package com.example.demo.services;
import java.util.List;
import com.example.demo.dto.*;

public interface AccountService {

    AccountResponse createAccount(CreateAccount request);

    AccountResponse getAccount(String accountNumber);

    AccountResponse deposit(String accountNumber, DepositWithdraw request);

    AccountResponse withdraw(String accountNumber, DepositWithdraw request);

    String transfer(Transfer request);
    Object modifyAccount(String accountNumber, UpdateDelete request);

    List<?> getTransactions(String accountNumber);  // return list of transactions
}
