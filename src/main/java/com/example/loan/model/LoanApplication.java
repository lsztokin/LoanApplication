package com.example.loan.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class LoanApplication 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private LocalDateTime creationTimestamp;
	
	@NotNull
	private double amount;
	
	@NotNull
	private int termInDays;
	
	@Enumerated(EnumType.STRING)
	private LoanApplicationStatus status;
	
	
	@OneToOne(mappedBy="loanApplication", cascade=CascadeType.ALL)
	private Loan loan;
	
	public LoanApplication() {};

	public LoanApplication(@NotNull double amount, @NotNull int termInDays) {
		super();
		this.amount = amount;
		this.termInDays = termInDays;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(LocalDateTime creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getTermInDays() {
		return termInDays;
	}

	public void setTermInDays(int termInDays) {
		this.termInDays = termInDays;
	}

	public LoanApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(LoanApplicationStatus status) {
		this.status = status;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	
	
}
