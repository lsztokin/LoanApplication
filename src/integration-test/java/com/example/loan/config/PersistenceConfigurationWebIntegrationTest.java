package com.example.loan.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfigurationWebIntegrationTest {
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource()
	{	return DataSourceBuilder.create().build();
	}
}
