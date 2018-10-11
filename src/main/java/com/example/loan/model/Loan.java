package com.example.loan.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Loan 
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "application_id", nullable=false)
	private LoanApplication loanApplication;
	
	@NotNull
	private Double amount;
	
	@NotNull
	private Double interestAmount;
	
	@NotNull
	private LocalDate startDate;
	
	@NotNull
	private LocalDate originalDueDate;
	
	@NotNull
	private LocalDate dueDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LoanApplication getLoanApplication() {
		return loanApplication;
	}

	public void setLoanApplication(LoanApplication loanApplication) {
		this.loanApplication = loanApplication;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getOriginalDueDate() {
		return originalDueDate;
	}

	public void setOriginalDueDate(LocalDate originalDueDate) {
		this.originalDueDate = originalDueDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
	}
	
	
	
	
}
