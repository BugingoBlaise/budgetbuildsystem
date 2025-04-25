package com.budgetbuildsystem.service.contractor;

import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Recommendation;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IContractorService {
    void saveContractor(Contractor contractor);

    Optional<Contractor> findByName(String contractorName);

    Optional<Contractor> findContractorByEmailAndUsername(String email, String username);

    List<Contractor> findAllContractors();
    Optional<Contractor>getContractorById(UUID uuid);

    double getAverageContractorRating();
    long countContractors();


    List<Contractor>findTopContractor();



    long getTotalContractors(Date startDate, Date endDate);
    double  calculateAverageRating(Date startDate, Date endDate);
    List<Contractor> findTopContractors(Date startDate, Date endDate);
    List<Recommendation> findAllReviews();
    long  getTotalReviews(Date startDate, Date endDate); // New method

}

