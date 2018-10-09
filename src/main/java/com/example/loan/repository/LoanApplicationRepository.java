package com.example.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loan.model.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> 
{
}
