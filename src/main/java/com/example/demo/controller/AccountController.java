package com.example.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.AccountResponse;
import com.example.demo.dto.*;
import com.example.demo.services.AccountService;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/createAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccount request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/getAccount/{accountNumber}")
    public AccountResponse getAccount(@PathVariable String accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    @PutMapping("/deposit/{accountNumber}")
    public AccountResponse deposit(
            @PathVariable String accountNumber,
            @Valid @RequestBody DepositWithdraw request) {
        return accountService.deposit(accountNumber, request);
    }

    @PutMapping("/withdraw/{accountNumber}")
    public AccountResponse withdraw(
            @PathVariable String accountNumber,
            @Valid @RequestBody DepositWithdraw request) {
        return accountService.withdraw(accountNumber, request);
    }

    @PostMapping("/transfer")
    public String transfer(@Valid @RequestBody Transfer request) {
        return accountService.transfer(request);
    }

    @GetMapping("/getTransactions/{accountNumber}")
    public Object getTransactions(@PathVariable String accountNumber) {
        return accountService.getTransactions(accountNumber);
    }
    
    @PutMapping("/modifyAccount/{accountNumber}")
    public Object modifyAccount(
            @PathVariable String accountNumber,
            @RequestBody UpdateDelete request) {

        return accountService.modifyAccount(accountNumber, request);
    }

}
