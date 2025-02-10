package com.budgetbuildsystem.service.loan;

import com.budgetbuildsystem.model.Loan;
import com.budgetbuildsystem.repository.ILoanRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class LoanServiceImpl implements ILoanService {
    @Autowired
    private ILoanRepository repository;

    @Override
    public List<Loan> getAllLoans() {
        return repository.findAll();
    }

    @Override
    public Optional<Loan> getLoanById(UUID id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan of ID {} not found" + id)));
    }

    @Override
    public Loan saveLoan(Loan loan) {
            return repository.save(loan);
    }

    @Override
    public void deleteLoanById(UUID id) {
        if (id != null) {
        repository.deleteById(id);
        }
    }

    @Override
    public List<Loan> searchLoanByName(String loanName) {
        if (loanName == null || loanName.trim().isEmpty()) {
            throw new IllegalArgumentException("Material name cannot be empty");
        }
        Optional<List<Loan>> materials = repository.findLoanByName(loanName.trim());
        return materials.orElseThrow(()->new EntityNotFoundException("Material not found"));
    }

    @Override
    public long getTotalLoans() {
        return repository.count();
    }


}
