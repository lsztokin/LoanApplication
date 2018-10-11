package com.example.loan.controller;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.loan.LoanApplicationStarter;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes= {LoanApplicationStarter.class})
//@Sql({"/insert-data.sql"})
//@Sql(scripts = "/clean-up.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
//@ActiveProfiles("test")
public class LoanApplicationControllerWebIntegrationTest 
{	
	@Test
	public void addLoanApplication()
	{	TestRestTemplate restTemplate = new TestRestTemplate();	
		ResponseEntity<String> response;
		
		response = restTemplate.postForEntity("http://localhost:8080/loan/add/1234/60", null,  String.class);
		
		assertThat( response.getBody(), startsWith("APPROVED:"));
	}

}
