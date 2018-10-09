package com.example.loan.controller;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.loan.service.LoanApplicationService;

public class LoanApplicationControllerTest 
{	
	@InjectMocks
	private LoanApplicationController loanApplicationController;
	
	@Mock
	private LoanApplicationService loanApplicationService;
	
	
	@Before
	public void init()
	{	MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void postLoanRequest()
	{	
		String expectedResponse = "accepted: 7";
		
		when(loanApplicationService.applyForLoan(100, 30)).thenReturn(expectedResponse);
		String response = loanApplicationController.addLoanPost(100.0, 30);
		
		assertThat(response, is(expectedResponse));
	}
	
	@Test
	public void extendLoanTerm()
	{
		long loanId = 7L;
		String expectedResponse = "accepted";
		when(loanApplicationService.extendLoanTerm(loanId)).thenReturn(expectedResponse);
		String response = loanApplicationController.extendLoanPost(loanId);
		
		assertThat(response, is(expectedResponse));
	}
	
	

}
