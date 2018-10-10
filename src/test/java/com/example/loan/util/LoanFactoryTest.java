package com.example.loan.util;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.loan.model.Loan;
import com.example.loan.model.LoanApplication;
import com.example.loan.param.LoanParameters;

public class LoanFactoryTest 
{	
	@InjectMocks
	private LoanFactory loanFactory;
	
	@Mock
	private LoanParameters loanParams;
	
	private LoanApplication application;
	private Loan expectedLoan;
	
	private final int INTEREST_RATE_IN_PERCENT = 10;
	private final LocalDateTime TIMESTAMP = LocalDateTime.of(2016, 5, 11, 10, 42);
	private final long APPLICATION_ID = 7L;
	private final double BASIC_AMOUNT = 542d;
	private final double BASIC_INTEREST_AMOUNT = 54.2d;
	private final int TERM = 16;
	
	@Before
	public void init()
	{	MockitoAnnotations.initMocks(this);
		
		when(loanParams.getInterestRateInPercent()).thenReturn(INTEREST_RATE_IN_PERCENT);
		
		application = new LoanApplication();
		application.setCreationTimestamp(TIMESTAMP);
		application.setId(APPLICATION_ID);
		application.setAmount(BASIC_AMOUNT);
		application.setTermInDays(TERM);
		
		expectedLoan = new Loan();
		expectedLoan.setAmount(BASIC_AMOUNT);
		expectedLoan.setInterestAmount(BASIC_INTEREST_AMOUNT);
		expectedLoan.setLoanApplication(application);
		expectedLoan.setStartDate(TIMESTAMP.toLocalDate());
		expectedLoan.setDueDate(TIMESTAMP.toLocalDate().plusDays(TERM));
		expectedLoan.setOriginalDueDate(TIMESTAMP.toLocalDate().plusDays(TERM));
	}
	
	
	@Test
	public void loanCreation()
	{	assertThat( loanFactory.createLoan(application), samePropertyValuesAs(expectedLoan));	
	}
	
	@Test
	public void loanCreationWithRoundedInterestAmount()
	{	application.setAmount(562.17d);
		expectedLoan.setAmount(562.17d);
		expectedLoan.setInterestAmount(56.22d);
		
		assertThat( loanFactory.createLoan(application), samePropertyValuesAs(expectedLoan));	
	}
}
