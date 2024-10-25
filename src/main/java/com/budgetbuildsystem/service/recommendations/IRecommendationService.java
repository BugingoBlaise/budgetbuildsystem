package com.budgetbuildsystem.service.recommendations;

import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Recommendation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecommendationService {

    public List<Contractor> listAllContractors();
    Optional<Contractor> getContractorById(UUID contractorId);
    Recommendation rateAndComment(UUID contractorId, int rating, String comment, UUID citizenId) ;
    void updateContractorRating(Contractor contractor);
    double calculateAverageRating(UUID contractorId);
    List<Recommendation> getReviewsForContractor(UUID contractorId);
}
