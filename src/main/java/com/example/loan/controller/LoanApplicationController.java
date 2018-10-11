package com.example.loan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.example.loan.service.LoanApplicationService;

@RestController
@RequestMapping("/loan")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoanApplicationController 
{
	@Autowired
	private LoanApplicationService loanApplicationService;
	
	@PostMapping("/add/{amount:[1-9]\\d*}/{termInDays:[1-9]\\d*}")
	public String addLoanPost(@PathVariable double amount, @PathVariable int termInDays)
	{	String result = loanApplicationService.applyForLoan(amount, termInDays);
		return result;
	}
	
	@PostMapping("/extend/{loanId:[1-9]\\d*}")
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
