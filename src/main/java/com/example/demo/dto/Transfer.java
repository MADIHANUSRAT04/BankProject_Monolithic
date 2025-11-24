package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transfer {

    @NotBlank
    private String sourceAccount;

    @NotBlank
    private String destinationAccount;

    @Positive(message = "Amount must be positive")
    private Double amount;
}

