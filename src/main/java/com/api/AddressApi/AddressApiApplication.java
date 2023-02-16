package com.api.AddressApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCaching
public class AddressApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(AddressApiApplication.class, args);
	}

}
