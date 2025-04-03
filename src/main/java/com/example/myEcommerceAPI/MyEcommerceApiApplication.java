package com.example.myEcommerceAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class MyEcommerceApiApplication {

	public static void main(String[] args) {
	
		SpringApplication.run(MyEcommerceApiApplication.class, args);
	}


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}	

}
