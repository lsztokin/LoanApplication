package com.example.loan.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.loan.model.Loan;
import com.example.loan.model.LoanApplication;
import com.example.loan.model.LoanApplicationStatus;
import com.example.loan.param.LoanParameters;
import com.example.loan.repository.LoanApplicationRepository;
import com.example.loan.repository.LoanRepository;
import com.example.loan.util.LoanApplicationFactory;
import com.example.loan.util.LoanFactory;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class LoanApplicationServiceOnLoanRepositories implements LoanApplicationService 
{
	@Autowired
	private LoanApplicationRepository loanApplicationRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private LoanParameters loanParams;
	
	@Autowired
	private LoanFactory loanFactory;
	
	@Autowired
	private LoanApplicationFactory loanApplicationFactory;
	
	@Override
	public String applyForLoan(double amount, int termInDays) 
	{	
		LoanApplication application = loanApplicationFactory.createLoanApplication(amount, termInDays);
		application = loanApplicationRepository.save(application);
		
		Long loanId = null;
		if(isApplicationAccepted(application))
		{	loanId = registerNewLoan(application);
		}
		
		return "" + application.getStatus() + ( loanId != null ? ":" + loanId : "");
	}
	
	private boolean isApplicationAccepted(LoanApplication application) 
	{	if(application.getStatus() != null && application.getStatus() == LoanApplicationStatus.APPROVED)
		{	return true;
		}
		return false;
	}

	private long registerNewLoan(LoanApplication application) 
	{	Loan loan = loanFactory.createLoan(application);
		loan = loanRepository.save(loan);
		
		return loan.getId();
	}

	@Override
	public String extendLoanTerm(long loanId) {
		
		Optional<Loan> loanOptional = loanRepository.findById(loanId);
		
		if(loanOptional.isPresent())
		{	Loan loan = loanOptional.get();
			LocalDate dueDate = loan.getDueDate();
			
			if(dueDate != null)
			{	dueDate = dueDate.plusDays(loanParams.getExtensionTermInDays());
				loan.setDueDate(dueDate);
				loanRepository.save(loan);
				
				return LoanApplicationStatus.APPROVED.toString();
			}
		}
		
		return LoanApplicationStatus.REJECTED.toString();
	}
}
