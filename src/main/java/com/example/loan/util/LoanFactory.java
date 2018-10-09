package com.example.loan.util;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.loan.model.Loan;
import com.example.loan.model.LoanApplication;
import com.example.loan.param.LoanParameters;

@Component
public class LoanFactory 
{
	@Autowired
	private LoanParameters loanParams;
	
	public Loan createLoan(LoanApplication application)
	{	
		Loan loan = new Loan();
		loan.setAmount(application.getAmount());
		loan.setStartDate(LocalDate.now());
		loan.setDueDate(LocalDate.now().plusDays(application.getTermInDays()));
		loan.setOriginalDueDate(LocalDate.now().plusDays(application.getTermInDays()));
		double interestAmount = calculateRoundedInterestAmount(application);
		loan.setInterestAmount(interestAmount);
		loan.setLoanApplication(application);
		
		return loan;
	}

	private double calculateRoundedInterestAmount(LoanApplication application) 
	{	
		int interestRateInPercent = loanParams.getInterestRateInPercent();
		double loanAmount = application.getAmount();
		double interestAmount = loanAmount * interestRateInPercent / 100.0;
		
		return (double)Math.round(interestAmount * 100d) / 100d;
	}
	
	
	
	
	

	

}
