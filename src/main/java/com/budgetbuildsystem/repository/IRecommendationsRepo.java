package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecommendationsRepo extends JpaRepository<Recommendation, UUID> {
    Optional<Recommendation> findContractorRecommendationsByContractorCompanyName(String name);

    List<Recommendation> findContractorRecommendationsByContractor_Id(UUID contractorId);
}
