package com.example.loan.service;


//import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.loan.model.Loan;
import com.example.loan.model.LoanApplication;
import com.example.loan.model.LoanApplicationStatus;
import com.example.loan.param.LoanParameters;
import com.example.loan.repository.LoanApplicationRepository;
import com.example.loan.repository.LoanRepository;
import com.example.loan.util.LoanApplicationFactory;
import com.example.loan.util.LoanFactory;

public class LoanApplicationServiceOnLoanRepositoriesTest 
{
	

	@InjectMocks
	private LoanApplicationServiceOnLoanRepositories service;
	
	@Mock
	private LoanApplicationRepository loanApplicationRepository;
	
	@Mock
	private LoanRepository loanRepository;
	
	@Mock
	private LoanParameters loanParams;
	
	@Mock
	private LoanFactory loanFactory;
	
	@Mock
	private LoanApplicationFactory loanApplicationFactory;
	
	@Captor
	private ArgumentCaptor<Loan> extendedLoanCaptor;
	
	private static final double AMOUNT = 534.16;
	private static final int TERM_IN_DAYS = 16;
	private static final double INTEREST_AMOUNT = 53.42;
	private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2018,  4, 1, 20, 16);
	private static final LocalDate DUE_DATE = LocalDate.of(2018, 4, 17);
	private static final LocalDate EXTENDED_DUE_DATE = LocalDate.of(2018, 4, 27);
	private static final long APPLICATION_ID = 17L;
	private static final long LOAN_ID = 146L;
	private static final String EXPECTED_RESPONSE_ACCAPTED_LOAN = "APPROVED:146";
	private static final String EXPECTED_RESPONSE_REJECTED_LOAN = "REJECTED";
	private static final String EXPECTED_RESPONSE_ACCAPTED_LOAN_EXTENSION = "APPROVED";
	private static final String EXPECTED_RESPONSE_REJECTED_LOAN_EXTENSION = "REJECTED";
	private static final Integer EXTENSION_TERM_IN_DAYS = 10;
	
	
	private LoanApplication application;
	private LoanApplication savedApplication;
	private Loan loan;
	private Loan savedLoan;
	private Loan expectedExtendedLoan;
	
	@Before
	public void init()
	{	MockitoAnnotations.initMocks(this);
	
		application = new LoanApplication(AMOUNT, TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP);
		application.setStatus(LoanApplicationStatus.APPROVED);
		
		savedApplication = new LoanApplication(AMOUNT, TERM_IN_DAYS);
		savedApplication.setCreationTimestamp(TIMESTAMP);
		savedApplication.setStatus(LoanApplicationStatus.APPROVED);
		savedApplication.setId(APPLICATION_ID);
		
		loan = new Loan();
		
		savedLoan = new Loan();
		savedLoan.setId(LOAN_ID);
		savedLoan.setDueDate(DUE_DATE);
		savedLoan.setOriginalDueDate(DUE_DATE);
		savedLoan.setStartDate(TIMESTAMP.toLocalDate());
		savedLoan.setInterestAmount(INTEREST_AMOUNT);
		
		expectedExtendedLoan = new Loan();
		expectedExtendedLoan.setId(LOAN_ID);
		expectedExtendedLoan.setDueDate(EXTENDED_DUE_DATE);
		expectedExtendedLoan.setOriginalDueDate(DUE_DATE);
		expectedExtendedLoan.setStartDate(TIMESTAMP.toLocalDate());
		expectedExtendedLoan.setInterestAmount(INTEREST_AMOUNT);
	
		when(loanApplicationFactory.createLoanApplication(AMOUNT, TERM_IN_DAYS)).thenReturn(application);
		when(loanApplicationRepository.save(application)).thenReturn( savedApplication );
		when(loanFactory.createLoan(savedApplication)).thenReturn(loan);
		when(loanRepository.save(loan)).thenReturn(savedLoan);
		when(loanParams.getExtensionTermInDays()).thenReturn(EXTENSION_TERM_IN_DAYS);
	}
	
	@Test
	public void applyForLoanWithPositiveResult() 
	{	
		assertThat( service.applyForLoan(AMOUNT, TERM_IN_DAYS), is(EXPECTED_RESPONSE_ACCAPTED_LOAN));
		
		verify( loanApplicationRepository).save(application);
		verify( loanRepository).save(loan);
	}
	
	
	@Test
	public void applyForLoanWithNegativeResult() 
	{	
		application.setStatus(LoanApplicationStatus.REJECTED);
		savedApplication.setStatus(LoanApplicationStatus.REJECTED);
		
		assertThat( service.applyForLoan(AMOUNT, TERM_IN_DAYS), is(EXPECTED_RESPONSE_REJECTED_LOAN));
		
		verify( loanApplicationRepository).save(application);
		verify( loanRepository, times(0)).save(loan);
	}
	
	@Test
	public void applyForLoanExtensionWithPositiveResult()
	{	
		when(loanRepository.findById(LOAN_ID)).thenReturn( Optional.of(savedLoan) );
		
		//Check method response
		assertThat( service.extendLoanTerm(LOAN_ID), is( EXPECTED_RESPONSE_ACCAPTED_LOAN_EXTENSION ));
		
		//Check updated object saved to repository
		verify( loanRepository).save(extendedLoanCaptor.capture());
		assertThat( extendedLoanCaptor.getValue(), samePropertyValuesAs(expectedExtendedLoan));
	}
	
	@Test
	public void applyForLoanExtensionWithPositiveResultUpdatedLoanCheck()
	{	
		when(loanRepository.findById(LOAN_ID)).thenReturn( Optional.empty() );
		
		assertThat( service.extendLoanTerm(LOAN_ID), is( EXPECTED_RESPONSE_REJECTED_LOAN_EXTENSION ));
		verify( loanRepository, times(0) ).save( any(Loan.class) );
	}
}
