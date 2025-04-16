package com.example.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.example.intern.controller")
public class InternApplication {
	public static void main(String[] args) {
		SpringApplication.run(InternApplication.class, args);
	}
}
