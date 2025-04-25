package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IContractorRepository extends JpaRepository<Contractor, UUID> {
    Optional<Contractor> findByCompanyName(String companyName);

    Optional<Contractor> findContractorByEmailAndUsername(String email, String username);

    @Query("SELECT AVG(c.averageRating) FROM Contractor c")
    double calculateAverageRating();

    @Query("SELECT c FROM Contractor c ORDER BY c.averageRating DESC")
    List<Contractor> findTopContractors();









    @Query("SELECT COUNT(DISTINCT c) FROM Contractor c JOIN c.review r " +
            "WHERE (cast(:startDate as date) IS NULL OR r.date >= :startDate) " +
            "AND (cast(:endDate as date) IS NULL OR r.date <= :endDate)")
    long countByReviewDateBetween(@Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate);

    @Query("SELECT AVG(r.rating) FROM Contractor c JOIN c.review r " +
            "WHERE (cast(:startDate as date) IS NULL OR r.date >= :startDate) " +
            "AND (cast(:endDate as date) IS NULL OR r.date <= :endDate)")
    Double calculateAverageRatingBetweenDates(@Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate);

    @Query("SELECT c FROM Contractor c JOIN c.review r "+
            "WHERE (cast(:startDate as date) IS NULL OR r.date >= :startDate) " +
            "AND (cast(:endDate as date) IS NULL OR r.date <= :endDate) " +
            "GROUP BY c ORDER BY AVG(r.rating) DESC")
    List<Contractor> findTopContractorsBetweenDates(@Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate);
}
