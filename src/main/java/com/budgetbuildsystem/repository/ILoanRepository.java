package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ILoanRepository extends JpaRepository<Loan, UUID> {

}
