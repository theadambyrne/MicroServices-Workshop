package com.johara.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
public class UserServiceApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "user-service");
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
