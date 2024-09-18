package com.budgetbuildsystem.service.recommendations;

import com.budgetbuildsystem.exception.EmailNotFound;
import com.budgetbuildsystem.model.Recommendation;
import com.budgetbuildsystem.model.Contractor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecommendationService {
   /* List<ContractorRecommendation> getReviewsForContractor(UUID contractorId);
    Optional<Local_Contractor> getContractorById(UUID contractorId);
    ContractorRecommendation addReview(UUID contractorId, ContractorRecommendation review);
    public double calculateAverageRating(UUID contractorId);
*/
    public List<Contractor> listAllContractors();
    Optional<Contractor> getContractorById(UUID contractorId);
    Recommendation rateAndComment(UUID contractorId, int rating, String comment, UUID citizenId) throws EmailNotFound;
    void updateContractorRating(Contractor contractor);
    double calculateAverageRating(UUID contractorId);
    List<Recommendation> getReviewsForContractor(UUID contractorId);
}
