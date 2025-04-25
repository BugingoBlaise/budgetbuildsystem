package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IRecommendationsRepo extends JpaRepository<Recommendation, UUID> {
    @Query("SELECT r FROM Recommendation r WHERE r.contractor.id = :id")
    List<Recommendation> findRecommendationsByContractorId(@Param("id") UUID id);
    @Query("SELECT r FROM Recommendation r WHERE r.contractor = :contractor")
    List<Recommendation> findRecommendationsByContractor(@Param("contractor") Contractor contractor);
//    List<Recommendation> findAll();
/*
    @Query("SELECT COUNT(r) FROM Recommendation r " +
            "WHERE (:startDate IS NULL OR r.date >= :startDate) " +
            "AND (:endDate IS NULL OR r.date <= :endDate)")
    long countByDateBetween(@Param("startDate") Date startDate,
                            @Param("endDate") Date endDate);*/



    @Query("SELECT COUNT(r) FROM Recommendation r " +
            "WHERE (cast(:startDate as date) IS NULL OR r.date >= :startDate) " +
            "AND (cast(:endDate as date) IS NULL OR r.date <= :endDate)")
    long countByDateBetween(@Param("startDate") Date startDate,
                            @Param("endDate") Date endDate);
}
