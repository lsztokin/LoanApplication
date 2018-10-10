package com.example.loan.util;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.loan.model.LoanApplication;
import com.example.loan.model.LoanApplicationStatus;
import com.example.loan.verification.LoanVerificaticator;

public class LoanApplicationFactoryTest {

	@InjectMocks
	private LoanApplicationFactory applicationFactory;
	
	@Mock
	private LoanVerificaticator loanVerificator;
	
	@Mock
	private TimestampFactory timestampFactory;
	
	private final LocalDateTime TIMESTAMP = LocalDateTime.of(2018, 10, 10, 9,54);
	private final double AMOUNT = 500.23;
	private final int TERM_IN_DAYS = 23;
	
	private LoanApplication expectedApplication;
	
	
	@Before
	public void init()
	{	MockitoAnnotations.initMocks(this);
	
		expectedApplication = new LoanApplication(AMOUNT, TERM_IN_DAYS);
		expectedApplication.setCreationTimestamp(TIMESTAMP);
	}
	
	@Test
	public void createAcceptedApplication()
	{	LoanApplicationStatus status = LoanApplicationStatus.APPROVED;
		expectedApplication.setStatus(status);
		
		when(timestampFactory.getCurrentDateTime()).thenReturn(TIMESTAMP);
		when(loanVerificator.accept((LoanApplication)notNull())).thenReturn(true);
		
		assertThat(applicationFactory.createLoanApplication(AMOUNT, TERM_IN_DAYS), samePropertyValuesAs(expectedApplication));
	}
	
	@Test
	public void createRejectedApplication()
	{	LoanApplicationStatus status = LoanApplicationStatus.REJECTED;
		expectedApplication.setCreationTimestamp(TIMESTAMP);
		expectedApplication.setStatus(status);
		
		when(timestampFactory.getCurrentDateTime()).thenReturn(TIMESTAMP);
		when(loanVerificator.accept((LoanApplication)notNull())).thenReturn(false);
		
		assertThat(applicationFactory.createLoanApplication(AMOUNT, TERM_IN_DAYS), samePropertyValuesAs(expectedApplication));
	}	
}
