package com.example.loan;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example.loan")
@EnableJpaRepositories("com.example.loan.repository")
@EnableTransactionManagement
public class ApplicationConfiguration 
{	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
	{	return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
	{	JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}
	
//	@Bean
//	//@Inject
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) throws IllegalArgumentException, NamingException
//	{	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setPersistenceUnitName("testdb");
//		
//	}
	
}
