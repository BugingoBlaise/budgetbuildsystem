package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IContractorRepository extends JpaRepository<Contractor, UUID> {
     Optional<Contractor> findByCompanyName(String companyName);
     Optional<Contractor> findContractorByEmailAndUsername(String email,String username);
/*
     @Query("SELECT AVG(r.rating) FROM Recommendation r WHERE r.contractor.id = :contractorId")
     Double findAverageRatingByContractorId(@Param("contractorId") UUID contractorId);*/
}
