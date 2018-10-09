package com.example.loan.verifivation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.loan.model.LoanApplication;
import com.example.loan.param.LoanParameters;
import com.example.loan.verification.BasicLoanVerificator;

public class BasicLoanVerificatorTest 
{	
	private final int EXTENSION_TERM_IN_DAYS = 10;
	private final double MIN_LOAN_AMOUNT = 100;
	private final double MAX_LOAN_AMOUNT = 10_000;
	private final int MIN_LOAN_TERM_IN_DAYS = 7;
	private final int MAX_LOAN_TERM_IN_DAYS = 60;
	private final LocalTime NIGHT_PERIOD_START = LocalTime.of(1, 0);
	private final LocalTime NIGHT_PERIOD_END = LocalTime.of(6, 0);
	private final int INTEREST_RATE_IN_PERCENT = 10;
	
	private final LocalDateTime TIMESTAMP_BEFORE_NIGHT_PERIOD = LocalDateTime.of(1992, 07, 11, 0, 59);
	private final LocalDateTime TIMESTAMP_IN_NIGHT_PERIOD = LocalDateTime.of(2050, 1, 1, 2, 30);
	private final LocalDateTime TIMESTAMP_AFTER_NIGHT_PERIOD = LocalDateTime.of(2010, 10, 23, 14, 20);
	private final LocalDateTime TIMESTAMP_START_OF_NIGHT = NIGHT_PERIOD_START.atDate(LocalDate.of(1915, 4, 11));
	private final LocalDateTime TIMESTAMP_END_OF_NIGHT = NIGHT_PERIOD_START.atDate(LocalDate.of(2018, 7, 28));
	
	private final double AMOUNT_BELOW_MIN_AMOUNT = 53.02;
	private final double AMOUNT_IN_ACCEPTABLE_RANGE = 5_234.17;
	private final double AMOUNT_OVER_MAX_AMOUNT = 17_400.11;
	
	private final int LOAN_TERM_BELOW_MIN_TERM = 4;
	private final int LOAN_TERM_IN_ACCEPTABLE_RANGE = 33;
	private final int LOAN_TERM_OVER_MAX_TERM = 75;
	
	@InjectMocks
	private BasicLoanVerificator loanVerificator;
	
	@Mock
	private LoanParameters loanParams;
	
	@Before
	public void init()
	{	MockitoAnnotations.initMocks(this);
	
		when(loanParams.getExtensionTermInDays()).thenReturn(EXTENSION_TERM_IN_DAYS);
		when(loanParams.getMinLoanAmount()).thenReturn(MIN_LOAN_AMOUNT);
		when(loanParams.getMaxLoanAmount()).thenReturn(MAX_LOAN_AMOUNT);
		when(loanParams.getMinLoanTermInDays()).thenReturn(MIN_LOAN_TERM_IN_DAYS);
		when(loanParams.getMaxLoanTermInDays()).thenReturn(MAX_LOAN_TERM_IN_DAYS);
		when(loanParams.getNightPeriodStartHour()).thenReturn(NIGHT_PERIOD_START);
		when(loanParams.getNightPeriodEndHour()).thenReturn(NIGHT_PERIOD_END);
		when(loanParams.getInterestRateInPercent()).thenReturn(INTEREST_RATE_IN_PERCENT);
		//when(loanParams.get).thenReturn();
	}
	
	
	@Test
	public void acceptDayApplicationAfterNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptDayApplicationWithMinAmountAfterNight()
	{	LoanApplication application = new LoanApplication(MIN_LOAN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptDayApplicationMaxAmountAfterNight()
	{	LoanApplication application = new LoanApplication(MAX_LOAN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptDayApplicationMinTermAfterNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, MIN_LOAN_TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptDayApplicationMaxTermAfterNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, MAX_LOAN_TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptDayApplicationBEFORE_Night()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_BEFORE_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptDayApplicationWithMinAmountBEFORE_Night()
	{	LoanApplication application = new LoanApplication(MIN_LOAN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_BEFORE_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptDayApplicationMaxAmountBEFORE_Night()
	{	LoanApplication application = new LoanApplication(MAX_LOAN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_BEFORE_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptDayApplicationMinTermBEFORE_Night()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, MIN_LOAN_TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP_BEFORE_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptDayApplicationMaxTermBEFORE_Night()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, MAX_LOAN_TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP_BEFORE_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectDayApplicationAmountBelowRangeAfterNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_BELOW_MIN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectDayApplicationAmountOverRangeAfterNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_OVER_MAX_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectDayApplicationTermBelowRangeAfterNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_BELOW_MIN_TERM);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectDayApplicationTermOverRangeAfterNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_OVER_MAX_TERM);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectDayApplicationWithAmountBelowrangeBeforeNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_BELOW_MIN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_BEFORE_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectDayApplicationAmountOverRangeBeforeNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_OVER_MAX_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_BEFORE_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectDayApplicationTermBelowRangeBeforeNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_BELOW_MIN_TERM);
		application.setCreationTimestamp(TIMESTAMP_BEFORE_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectDayApplicationTermOverRangeBeforeNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_OVER_MAX_TERM);
		application.setCreationTimestamp(TIMESTAMP_BEFORE_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationStartOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_START_OF_NIGHT);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationWithMinAmountStartOfNight()
	{	LoanApplication application = new LoanApplication(MIN_LOAN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_START_OF_NIGHT);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationMaxAmountStartOfNight()
	{	LoanApplication application = new LoanApplication(MAX_LOAN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_START_OF_NIGHT);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationMinTermStartOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, MIN_LOAN_TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP_START_OF_NIGHT);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationMaxTermStartOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, MAX_LOAN_TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP_START_OF_NIGHT);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationEndOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_END_OF_NIGHT);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationWithMinAmountEndOfNight()
	{	LoanApplication application = new LoanApplication(MIN_LOAN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_END_OF_NIGHT);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationMaxAmountEndOfNight()
	{	LoanApplication application = new LoanApplication(MAX_LOAN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_END_OF_NIGHT);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationMinTermEndOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, MIN_LOAN_TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP_END_OF_NIGHT);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationMaxTermEndOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, MAX_LOAN_TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP_END_OF_NIGHT);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationMidleOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_IN_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationWithMinAmountMidleOfNight()
	{	LoanApplication application = new LoanApplication(MIN_LOAN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_IN_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationMaxAmountMidleOfNight()
	{	LoanApplication application = new LoanApplication(MAX_LOAN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_IN_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationMinTermMidleOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, MIN_LOAN_TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP_IN_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
	
	@Test
	public void acceptNightApplicationMaxTermMidleOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, MAX_LOAN_TERM_IN_DAYS);
		application.setCreationTimestamp(TIMESTAMP_IN_NIGHT_PERIOD);
		
		assertTrue(loanVerificator.accept(application));
	}
		
	@Test
	public void rejectNightApplicationAmountBelowRangeMiddleOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_BELOW_MIN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationAmountOverRangeMiddleOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_OVER_MAX_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationTermBelowRangeMiddleOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_BELOW_MIN_TERM);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationTermOverRangeMiddleOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_OVER_MAX_TERM);
		application.setCreationTimestamp(TIMESTAMP_AFTER_NIGHT_PERIOD);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationWithAmountBelowrangeEndOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_BELOW_MIN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_END_OF_NIGHT);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationAmountOverRangeEndOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_OVER_MAX_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_END_OF_NIGHT);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationTermBelowRangeEndOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_BELOW_MIN_TERM);
		application.setCreationTimestamp(TIMESTAMP_END_OF_NIGHT);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationTermOverRangeEndOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_OVER_MAX_TERM);
		application.setCreationTimestamp(TIMESTAMP_END_OF_NIGHT);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationWithAmountBelowrangeStartOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_BELOW_MIN_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_START_OF_NIGHT);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationAmountOverRangeStartOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_OVER_MAX_AMOUNT, LOAN_TERM_IN_ACCEPTABLE_RANGE);
		application.setCreationTimestamp(TIMESTAMP_START_OF_NIGHT);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationTermBelowRangeStartOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_BELOW_MIN_TERM);
		application.setCreationTimestamp(TIMESTAMP_START_OF_NIGHT);
		
		assertFalse(loanVerificator.accept(application));
	}
	
	@Test
	public void rejectNightApplicationTermOverRangeStartOfNight()
	{	LoanApplication application = new LoanApplication(AMOUNT_IN_ACCEPTABLE_RANGE, LOAN_TERM_OVER_MAX_TERM);
		application.setCreationTimestamp(TIMESTAMP_START_OF_NIGHT);
		
		assertFalse(loanVerificator.accept(application));
	}	
}
