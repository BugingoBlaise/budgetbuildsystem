package com.budgetbuildsystem.service.recommendationsAndReviews;

import com.budgetbuildsystem.exception.EmailNotFound;
import com.budgetbuildsystem.model.ContractorRecommendation;
import com.budgetbuildsystem.model.Local_Contractor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRecommendationService {
   /* List<ContractorRecommendation> getReviewsForContractor(UUID contractorId);
    Optional<Local_Contractor> getContractorById(UUID contractorId);
    ContractorRecommendation addReview(UUID contractorId, ContractorRecommendation review);
    public double calculateAverageRating(UUID contractorId);
*/
    public List<Local_Contractor> listAllContractors();
    Optional<Local_Contractor> getContractorById(UUID contractorId);
    ContractorRecommendation rateAndComment(UUID contractorId, int rating, String comment, UUID citizenId) throws EmailNotFound;
    void updateContractorRating(Local_Contractor contractor);
    double calculateAverageRating(UUID contractorId);
    List<ContractorRecommendation> getReviewsForContractor(UUID contractorId);
}
