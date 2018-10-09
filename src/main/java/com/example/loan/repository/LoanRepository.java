package com.example.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loan.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

}
