package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankSystemMonolithicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSystemMonolithicApplication.class, args);
		System.out.println("Banking system is working fine");
	}

}
