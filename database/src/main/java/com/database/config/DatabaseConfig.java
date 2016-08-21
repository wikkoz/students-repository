package com.database.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/repository.properties")
public class DatabaseConfig {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(environment.getProperty("spring.datasource.drivername"));
		dataSource.setUrl(environment.getProperty("spring.datasource.url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));

		return dataSource;
	}

	@Bean
	public SpringLiquibase liquibase()  {
		SpringLiquibase liquibase = new SpringLiquibase();

		liquibase.setDataSource(dataSource());
		liquibase.setDropFirst(Boolean.parseBoolean(environment.getProperty("database.drop")));
		liquibase.setChangeLog("classpath:/db.changelog-master.xml");

		return liquibase;
	}
}
