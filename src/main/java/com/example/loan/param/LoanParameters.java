package com.example.loan.param;

import java.time.LocalTime;

public interface LoanParameters 
{
	int getExtensionTermInDays();

	double getMinLoanAmount();

	double getMaxLoanAmount();

	int getMinLoanTermInDays();

	int getMaxLoanTermInDays();

	LocalTime getNightPeriodStartHour();

	LocalTime getNightPeriodEndHour();
	
	int getInterestRateInPercent();
	

}