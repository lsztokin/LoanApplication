package com.example.loan.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loan.model.Loan;
import com.example.loan.model.LoanApplication;
import com.example.loan.model.LoanApplicationStatus;
import com.example.loan.param.LoanParameters;
import com.example.loan.repository.LoanApplicationRepository;
import com.example.loan.repository.LoanRepository;
import com.example.loan.util.TimestampFactory;
import com.example.loan.verification.LoanVerificaticator;

@Service
public class LoanApplicationServiceOnLoanRepositories implements LoanApplicationService 
{
	@Autowired
	LoanApplicationRepository loanApplicationRepository;
	
	@Autowired
	LoanRepository loanRepository;
	
	@Autowired
	LoanVerificaticator verificator;
	
	@Autowired
	LoanParameters loanParams;
	
	@Autowired
	TimestampFactory timestampFactory;
	

	@Override
	public String applyForCredit(double amount, int termInDays) 
	{	LoanApplication application = new LoanApplication(amount, termInDays);
		application.setCreationTimestamp(timestampFactory.getCurrentDateTime());
		setApplicationStatus(application);
		
		application = loanApplicationRepository.save(application);
		
		
		Long loanId = null;
		if(isApplicationAccepted(application))
		{	loanId = registerNewLoan(application);
		}
		
		return "" + application.getStatus() + ( loanId != null ? ":" + loanId : "");
	}

	private void setApplicationStatus(LoanApplication application) 
	{	boolean isApplicationAccepted = verificator.accept(application);
		
		if(isApplicationAccepted)
		{	application.setStatus(LoanApplicationStatus.APPROVED);
		}
		else
		{	application.setStatus(LoanApplicationStatus.REJECTED);
		}
	}
	
	private boolean isApplicationAccepted(LoanApplication application) 
	{	if(application.getStatus() != null && application.getStatus() == LoanApplicationStatus.APPROVED)
		{	return true;
		}
		return false;
	}

	private long registerNewLoan(LoanApplication application) 
	{	Loan loan = new Loan();
		loan.setAmount(application.getAmount());
		loan.setStartDate(LocalDate.now());
		loan.setDueDate(LocalDate.now().plusDays(application.getTermInDays()));
		loan.setOriginalDueDate(LocalDate.now().plusDays(application.getTermInDays()));
		loan.setLoanApplication(application);
		
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
			}
			
			return LoanApplicationStatus.APPROVED.toString();
		}
		
		return LoanApplicationStatus.REJECTED.toString();
	}
}
