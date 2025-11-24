package com.example.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "accounts")
public class Account {

    @Id
    private String id;

    private String accountNumber;
    private String holderName;
    private Double balance;
    private String status; //Active or Inactive
    private Instant createdAt;
}
