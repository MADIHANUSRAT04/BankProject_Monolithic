package com.example.demo.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositWithdraw {

    @Positive(message = "Amount must be greater than zero")
    private Double amount;
}
