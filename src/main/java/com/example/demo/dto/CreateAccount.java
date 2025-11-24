package com.example.demo.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccount {

    @NotBlank(message = "Holder name cannot be empty")
    private String holderName;
}
