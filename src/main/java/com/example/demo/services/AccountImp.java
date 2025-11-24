package com.example.demo.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.dto.AccountResponse;
import com.example.demo.dto.CreateAccount;
import com.example.demo.dto.DepositWithdraw;
import com.example.demo.dto.Transfer;
import com.example.demo.dto.UpdateDelete;
import com.example.demo.exceptions.*;
import com.example.demo.model.*;
import com.example.demo.repo.*;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountImp implements AccountService {

	 private final AccountRepo accountRepository;
	    private final TransactionRepo transactionRepository;
	    private final TransactionService transactionService;

	    private String generateAccountNumber(String name) {
	        String initials = name.substring(0, 3).toUpperCase();
	        String random = String.valueOf((int) (Math.random() * 9000) + 1000);
	        return initials + random;
	    }

	    private Account findAccount(String accountNumber) {
	        log.info("Fetching account: {}", accountNumber);

	        Account acc = accountRepository.findByAccountNumber(accountNumber);
	        if (acc == null) {
	            log.error("Account not found: {}", accountNumber);
	            throw new AccountNotFoundException("Account not found: " + accountNumber);
	        }

	        return acc;
	    }

	    @Override
	    public AccountResponse createAccount(CreateAccount request) {

	        log.info("Creating new account for holder: {}", request.getHolderName());

	        String accNo = generateAccountNumber(request.getHolderName());

	        Account account = Account.builder()
	                .accountNumber(accNo)
	                .holderName(request.getHolderName())
	                .balance(0.0)
	                .status("ACTIVE")
	                .createdAt(Instant.now())
	                .build();

	        accountRepository.save(account);

	        log.info("Account created successfully with number: {}", accNo);

	        return AccountResponse.builder()
	                .accountNumber(accNo)
	                .holderName(request.getHolderName())
	                .balance(0.0)
	                .status("ACTIVE")
	                .build();
	    }

	    @Override
	    public AccountResponse getAccount(String accountNumber) {

	        log.info("Request received to fetch account: {}", accountNumber);

	        Account acc = findAccount(accountNumber);

	        log.info("Account found: {} | Holder: {}", acc.getAccountNumber(), acc.getHolderName());

	        return AccountResponse.builder()
	                .accountNumber(acc.getAccountNumber())
	                .holderName(acc.getHolderName())
	                .balance(acc.getBalance())
	                .status(acc.getStatus())
	                .build();
	    }

	    @Override
	    public AccountResponse deposit(String accountNumber, DepositWithdraw request) {

	        log.info("Depositing {} into account {}", request.getAmount(), accountNumber);

	        if (request.getAmount() <= 0) {
	            log.error("Invalid deposit amount: {}", request.getAmount());
	            throw new InvalidAmountException("Amount must be positive");
	        }

	        Account acc = findAccount(accountNumber);

	        acc.setBalance(acc.getBalance() + request.getAmount());
	        accountRepository.save(acc);

	        log.info("Deposit successful. New balance for {} is {}", accountNumber, acc.getBalance());

	        transactionService.recordDeposit(accountNumber, request.getAmount());

	        return AccountResponse.builder()
	                .accountNumber(acc.getAccountNumber())
	                .holderName(acc.getHolderName())
	                .balance(acc.getBalance())
	                .status(acc.getStatus())
	                .build();
	    }

	    @Override
	    public AccountResponse withdraw(String accountNumber, DepositWithdraw request) {

	        log.info("Withdrawing {} from account {}", request.getAmount(), accountNumber);

	        if (request.getAmount() <= 0) {
	            log.error("Invalid withdrawal amount: {}", request.getAmount());
	            throw new InvalidAmountException("Amount must be positive");
	        }

	        Account acc = findAccount(accountNumber);

	        if (acc.getBalance() < request.getAmount()) {
	            log.error("Insufficient balance for withdrawal. Current: {}, Requested: {}",
	                    acc.getBalance(), request.getAmount());
	            throw new InsufficientBalanceException("Insufficient funds");
	        }

	        acc.setBalance(acc.getBalance() - request.getAmount());
	        accountRepository.save(acc);

	        log.info("Withdrawal successful. New balance for {} is {}", accountNumber, acc.getBalance());

	        transactionService.recordWithdraw(accountNumber, request.getAmount());

	        return AccountResponse.builder()
	                .accountNumber(acc.getAccountNumber())
	                .holderName(acc.getHolderName())
	                .balance(acc.getBalance())
	                .status(acc.getStatus())
	                .build();
	    }

	    @Override
	    public String transfer(Transfer request) {

	        log.info("Transferring {} from {} to {}",
	                request.getAmount(), request.getSourceAccount(), request.getDestinationAccount());

	        if (request.getAmount() <= 0) {
	            log.error("Invalid transfer amount {}", request.getAmount());
	            throw new InvalidAmountException("Amount must be positive");
	        }

	        Account src = findAccount(request.getSourceAccount());
	        Account dest = findAccount(request.getDestinationAccount());

	        if (src.getBalance() < request.getAmount()) {
	            log.error("Insufficient balance for transfer. Current: {}, Requested: {}",
	                    src.getBalance(), request.getAmount());
	            throw new InsufficientBalanceException("Insufficient balance");
	        }

	        src.setBalance(src.getBalance() - request.getAmount());
	        dest.setBalance(dest.getBalance() + request.getAmount());

	        accountRepository.save(src);
	        accountRepository.save(dest);

	        log.info("Transfer successful from {} to {}", src.getAccountNumber(), dest.getAccountNumber());

	        transactionService.recordTransfer(src.getAccountNumber(), dest.getAccountNumber(), request.getAmount());

	        return "Transfer successful";
	    }

	    @Override
	    public List<?> getTransactions(String accountNumber) {

	        log.info("Fetching transactions for account {}", accountNumber);

	        findAccount(accountNumber);

	        List<Transaction> outgoing = transactionRepository.findBySourceAccount(accountNumber);
	        List<Transaction> incoming = transactionRepository.findByDestinationAccount(accountNumber);

	        log.info("Found {} outgoing and {} incoming transactions",
	                outgoing.size(), incoming.size());

	        outgoing.addAll(incoming);
	        return outgoing;
	    }
	    
	    @Override
	    public Object modifyAccount(String accountNumber, UpdateDelete request) {

	        log.info("Modify request for account {} with action {}", accountNumber, request.getAction());

	        Account acc = findAccount(accountNumber);

	        if (request.getAction().equalsIgnoreCase("UPDATE")) {

	            if (request.getHolderName() != null && !request.getHolderName().isBlank()) {
	                acc.setHolderName(request.getHolderName());
	            }

	            if (request.getStatus() != null) {
	                acc.setStatus(request.getStatus());
	            }

	            accountRepository.save(acc);

	            log.info("Account {} updated", accountNumber);

	            return AccountResponse.builder()
	                    .accountNumber(acc.getAccountNumber())
	                    .holderName(acc.getHolderName())
	                    .balance(acc.getBalance())
	                    .status(acc.getStatus())
	                    .build();
	        }

	        else if (request.getAction().equalsIgnoreCase("DELETE")) {

	            accountRepository.delete(acc);

	            log.info("Account {} deleted", accountNumber);

	            return "Account deleted successfully";
	        }

	        else {
	            throw new InvalidAmountException("Invalid action. Use UPDATE or DELETE.");
	        }
	    }

	}
