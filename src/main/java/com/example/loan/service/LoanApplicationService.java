package com.example.loan.service;

import com.example.loan.model.LoanApplication;

public interface LoanApplicationService 
{	long saveApplication(LoanApplication loanApplication); 

	String applyForCredit(double amount, int termInDays);
	
	String extendCreditTerm(long creditId);

}
