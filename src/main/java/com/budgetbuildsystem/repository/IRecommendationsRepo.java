package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IRecommendationsRepo extends JpaRepository<Recommendation, UUID> {
    @Query("SELECT r FROM Recommendation r WHERE r.contractor = :contractor")
    List<Recommendation> findRecommendationsByContractor(@Param("contractor") Contractor contractor);
}
