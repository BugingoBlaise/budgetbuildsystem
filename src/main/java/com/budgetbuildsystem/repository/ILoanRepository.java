package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILoanRepository extends JpaRepository<Loan, UUID> {

    @Query("SELECT l FROM Loan l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :kw, '%'))")
    Optional<List<Loan>> findLoanByName(@Param("kw") String name);

}
