package com.example.loan.controller;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {LoanApplicationStarter.class, PersistenceConfigurationWebIntegrationTest.class})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@Sql({"/insert-data.sql"})
//@Sql(scripts = "/clean-up.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
public class LoanApplicationControllerWebIntegrationTest 
{	
	@Test
	public void addLoanApplication()
	{	TestRestTemplate restTemplate = new TestRestTemplate();
	
		int amount = 1000;
		int term = 10;
		
		ResponseEntity<String> response;
		
		for(int i=0; i<10; i++)
		{	String url = "http://localhost:8080/loan/add/" + amount++ + "/" + term++;
			restTemplate.getForEntity(url, String.class);
		}
		
		//response = restTemplate.getForEntity("http://localhost:8080/loan/add/1234/60", String.class);
		response = restTemplate.postForEntity("http://localhost:8080/loan/add/1234/60", null,  String.class);
		
		//assertThat( response.getStatusCode() , equalTo(HttpStatus.OK));
		//assertThat( response.getBody() , equalTo( "expectedBody..."));
		
		assertThat( response.getBody(), startsWith("ACCEPTED:"));
	}

}
