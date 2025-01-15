package com.budgetbuildsystem.service.loan;

import com.budgetbuildsystem.model.Loan;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILoanService {
    List<Loan> getAllLoans();

    Optional<Loan> getLoanById(UUID id);

    Loan saveLoan(Loan regulation);

    void deleteLoanById(UUID id);
    public List<Loan> searchLoanByName(String loanName);
}
