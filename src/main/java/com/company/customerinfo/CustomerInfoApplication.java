package com.company.customerinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication( exclude = {DataSourceAutoConfiguration.class} )
public class CustomerInfoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerInfoApplication.class.getName());

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(CustomerInfoApplication.class);
		application.run(args);

		LOGGER.info("Spring Boot application started!");
	}

}