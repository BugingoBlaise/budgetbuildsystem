package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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


}
