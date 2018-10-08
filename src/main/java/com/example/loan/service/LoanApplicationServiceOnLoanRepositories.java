package com.example.loan.service;

import org.springframework.stereotype.Service;

import com.example.loan.model.LoanApplication;

@Service
public class LoanApplicationServiceOnLoanRepositories implements LoanApplicationService 
{
	@Override
	public long saveApplication(LoanApplication loanApplication) 
	{	// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String applyForCredit(double amount, int termInDays) {
		// TODO Auto-generated method stub
		return "0";
	}

	@Override
	public String extendCreditTerm(long creditId) {
		// TODO Auto-generated method stub
		return null;
	}

}
