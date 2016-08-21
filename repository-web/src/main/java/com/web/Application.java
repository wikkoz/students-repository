package com.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude={LiquibaseAutoConfiguration.class})
@ComponentScan("com")
@PropertySource("classpath:/repository.properties")

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
