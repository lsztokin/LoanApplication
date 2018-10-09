package com.example.loan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loan.service.LoanApplicationService;

@RestController
@RequestMapping("/loan")
public class LoanApplicationController 
{
	@Autowired
	private LoanApplicationService loanApplicationService;
	
	@GetMapping("/add/{amount:[1-9]\\d*}/{termInDays:[1-9]\\d*}")
	public String addLoanPost(@PathVariable double amount, @PathVariable int termInDays)
	{	String result = loanApplicationService.applyForLoan(amount, termInDays);
		return result;
	}
	
	@GetMapping("/extend/{loanId}")
	public String extendLoanPost(@PathVariable long loanId)
	{	
		return loanApplicationService.extendLoanTerm(loanId);
	}
	
	@ExceptionHandler(Exception.class)
	public String handleError(HttpServletRequest req, Exception ex) 
	{   System.out.println("Request: " + req.getRequestURL() + " raised " + ex);   
	
		return "Error...";
	}
}
