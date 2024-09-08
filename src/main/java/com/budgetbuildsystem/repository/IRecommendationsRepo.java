package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.ContractorRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecommendationsRepo extends JpaRepository<ContractorRecommendation, UUID> {
    Optional<ContractorRecommendation> findContractorRecommendationsByContractorCompanyName(String name);

    List<ContractorRecommendation> findContractorRecommendationsByContractor_Id(UUID contractorId);
}
