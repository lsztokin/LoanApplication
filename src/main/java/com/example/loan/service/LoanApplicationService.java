package com.example.loan.service;

public interface LoanApplicationService 
{	String applyForLoan(double amount, int termInDays);
	String extendLoanTerm(long creditId);
}
