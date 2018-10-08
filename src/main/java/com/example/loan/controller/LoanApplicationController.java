package com.example.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loan.service.LoanApplicationService;

@RestController
@RequestMapping("/loan")
public class LoanApplicationController 
{
	@Autowired
	private LoanApplicationService loanApplicationService;
	
	@PostMapping("/add/{amount}/{termInDays}")
	public String addLoanPost(@PathVariable double amount, @PathVariable int termInDays)
	{	
		String result = loanApplicationService.applyForCredit(amount, termInDays);
		return result;
	}
	
	@GetMapping("/add/{amount}/{termInDays}")
	public String addLoanGet(@PathVariable double amount, @PathVariable int termInDays)
	{	
		String result = "GET Zawnioskowano o " + amount + " na " + termInDays + " dni.";
		return result;
	}
	
	@PostMapping("/extend/{loanId}")
	public String extendLoanPost(@PathVariable long loanId)
	{	
		return loanApplicationService.extendCreditTerm(loanId);
	}
}
