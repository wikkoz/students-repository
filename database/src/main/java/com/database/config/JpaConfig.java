package com.database.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.database.repository")
@PropertySource("classpath:/repository.properties")
@EntityScan("com.database.entity")
public class JpaConfig {
}
