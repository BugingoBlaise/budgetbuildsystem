package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Local_Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IContractorRepository extends JpaRepository<Local_Contractor, UUID> {
     Optional<Local_Contractor> findByCompanyName(String companyName);
}
