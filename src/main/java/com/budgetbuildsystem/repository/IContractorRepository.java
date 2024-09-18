package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IContractorRepository extends JpaRepository<Contractor, UUID> {
     Optional<Contractor> findByCompanyName(String companyName);
}
