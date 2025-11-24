package com.example.demo.dto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountResponse {
    private String accountNumber;
    private String holderName;
    private Double balance;
    private String status;
}

