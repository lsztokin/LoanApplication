package com.example.loan.param;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

@Service
public class BasicLoanParameters implements LoanParameters
{
	private static final int EXTENSION_TERM_IN_DAYS = 10;
	private static final double MIN_LOAN_AMOUNT = 100;
	private static final double MAX_LOAN_AMOUNT = 10_000;
	private static final int MIN_LOAN_TERM_IN_DAYS = 7;
	private static final int MAX_LOAN_TERM_IN_DAYS = 60;
	private static final LocalTime NIGHT_PERIOD_START = LocalTime.of(0, 0);
	private static final LocalTime NIGHT_PERIOD_END = LocalTime.of(6, 0);
	private static final int INTEREST_RATE_IN_PERCENT = 10;
	
	@Override
	public int getExtensionTermInDays() {
		return EXTENSION_TERM_IN_DAYS;
	}

	@Override
	public double getMinLoanAmount() {
		return MIN_LOAN_AMOUNT;
	}

	@Override
	public double getMaxLoanAmount() {
		return MAX_LOAN_AMOUNT;
	}

	@Override
	public int getMinLoanTermInDays() {
		return MIN_LOAN_TERM_IN_DAYS;
	}

	@Override
	public int getMaxLoanTermInDays() {
		return MAX_LOAN_TERM_IN_DAYS;
	}

	@Override
	public LocalTime getNightPeriodStartHour() {
		return NIGHT_PERIOD_START;
	}

	@Override
	public LocalTime getNightPeriodEndHour() {
		return NIGHT_PERIOD_END;
	}

	@Override
	public int getInterestRateInPercent() {
		return INTEREST_RATE_IN_PERCENT;
	}
}
