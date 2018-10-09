package com.example.loan.service;

import com.example.loan.model.LoanApplication;

public interface LoanApplicationService 
{	String applyForCredit(double amount, int termInDays);
	String extendLoanTerm(long creditId);
}
