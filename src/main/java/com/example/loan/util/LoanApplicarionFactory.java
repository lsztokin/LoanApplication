package com.example.loan.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.loan.model.LoanApplication;
import com.example.loan.model.LoanApplicationStatus;
import com.example.loan.verification.LoanVerificaticator;

public class LoanApplicarionFactory 
{	
	@Autowired
	private LoanVerificaticator loanVerificator;
	
	@Autowired
	private TimestampFactory timestampFactory;
	
	public LoanApplication createLoanApplication(double amount, int termInDays)
	{			
		LoanApplication application = new LoanApplication(amount, termInDays);
		application.setCreationTimestamp(timestampFactory.getCurrentDateTime());
		setApplicationStatus(application);
		
		return application;
	}
	
	private void setApplicationStatus(LoanApplication application) 
	{	boolean isApplicationAccepted = loanVerificator.accept(application);
		
		if(isApplicationAccepted)
		{	application.setStatus(LoanApplicationStatus.APPROVED);
		}
		else
		{	application.setStatus(LoanApplicationStatus.REJECTED);
		}
	}
}
