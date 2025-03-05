package com.example.spring_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.spring_rest.dto")
public class SpringRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestApplication.class, args);
	}

}
