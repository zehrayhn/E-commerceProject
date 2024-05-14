package com.example.ecommerce;

import com.example.ecommerce.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

public class ECommerceApplication {
//	private static final Logger logger=  LogManager.getLogger(UserService.class);
	public static void main(String[] args) {
//		logger.info("helloo");
//		logger.debug("hello");
		SpringApplication.run(
				ECommerceApplication.class, args);
	}

}
