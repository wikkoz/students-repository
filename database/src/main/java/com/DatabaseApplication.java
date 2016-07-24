package com;

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
public class DatabaseApplication {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(environment.getProperty("database.drivername"));
		dataSource.setUrl(environment.getProperty("database.url"));
		dataSource.setUsername(environment.getProperty("database.username"));
		dataSource.setPassword(environment.getProperty("database.password"));

		return dataSource;
	}

	@Bean
	public SpringLiquibase liquibase()  {
		SpringLiquibase liquibase = new SpringLiquibase();

		liquibase.setDataSource(dataSource());
		liquibase.setChangeLog("classpath:/db.changelog-master.xml");
		liquibase.setDropFirst(Boolean.getBoolean(environment.getProperty("database.drop")));

		return liquibase;
	}
}
