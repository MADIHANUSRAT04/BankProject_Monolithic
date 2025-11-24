package com.example.demo.repo;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Account;

public interface AccountRepo extends MongoRepository<Account, String> {

    Account findByAccountNumber(String accountNumber);
}
