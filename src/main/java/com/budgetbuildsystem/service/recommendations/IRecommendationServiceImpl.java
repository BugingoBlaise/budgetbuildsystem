package com.budgetbuildsystem.service.recommendations;

import com.budgetbuildsystem.model.Citizen;
import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Recommendation;
import com.budgetbuildsystem.repository.IContractorRepository;
import com.budgetbuildsystem.repository.IRecommendationsRepo;
import com.budgetbuildsystem.service.citizen.ICitizenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class IRecommendationServiceImpl implements IRecommendationService {
    private final IRecommendationsRepo recommendationRepository;
    private final IContractorRepository contractorRepository;
    private final ICitizenService citizenService;

   /* public Optional<ContractorRecommendation> getReviewsForContractor(UUID contractorId) {
        return recommendationRepository.findById(contractorId);
    }

    public Optional<Local_Contractor> getContractorById(UUID contractorId) {
        return contractorRepository.findById(contractorId);
    }

    public ContractorRecommendation addReview(UUID contractorId, ContractorRecommendation review) {
        Optional<Local_Contractor> contractor = contractorRepository.findById(contractorId);

    if (contractor.isPresent()) {
            review.setRating(0);
            review.setLikeCount(0);
            review.setDate(new Date());

            review.setContractor(contractor.get());
            return recommendationRepository.save(review);
        }
        return null;
    }

    public double calculateAverageRating(UUID contractorId) {
        List<ContractorRecommendation> reviews = recommendationRepository.findContractorRecommendationsByContractor_Id(contractorId);
        return reviews.stream()
                .mapToInt(ContractorRecommendation::getRating)
                .average()
                .orElse(0.0);
    }*/

    // List all contractors
    public List<Contractor> listAllContractors() {
        return contractorRepository.findAll();
    }

    // Get contractor details by ID
    public Optional<Contractor> getContractorById(UUID contractorId) {
        return contractorRepository.findById(contractorId);
    }

    // Rate and comment on a contractor
    public Recommendation rateAndComment(
            UUID contractorId,
            String comment,
            int rating,

            UUID citizenId) {
        Optional<Contractor> contractorOptional = contractorRepository.findById(contractorId);
        Optional<Citizen> citizen = citizenService.getCitizenById(citizenId);
        if (contractorOptional.isPresent()) {
            Contractor contractor = contractorOptional.get();
            Recommendation newReview = new Recommendation();
            newReview.setRating(rating);
            newReview.setReviews(Collections.singletonList(comment));
            newReview.setContractor(contractor);
            citizen.ifPresent(newReview::setCitizen);// Assuming Citizen ID is passed in for reference
            newReview.setDate(new java.util.Date());
            newReview.setLikeCount(newReview.getLikeCount()+1);
            // Save the new review
            Recommendation savedReview = recommendationRepository.save(newReview);
            // Update the contractor's reviews list and average rating
            contractor.getReview().add(savedReview);
            updateContractorRating(contractor);
            return savedReview;
        } else {
            throw new EntityNotFoundException("Contractor not found with ID: " + contractorId);
        }
    }

    // Calculate and update the contractor's average rating
    public void updateContractorRating(Contractor contractor) {
        float averageRating = (float) calculateAverageRating(contractor.getId());
        contractor.setAverageRating(averageRating);
        contractorRepository.save(contractor);
    }

    // Calculate the average rating for a contractor
    public double calculateAverageRating(UUID contractorId) {
        List<Recommendation> reviews =
                recommendationRepository.findContractorRecommendationsByContractor_Id(contractorId);
        return (float) reviews.stream()
                .mapToInt(Recommendation::getRating)
                .average()
                .orElse(0.0);
    }

    // Get all reviews for a contractor
    public List<Recommendation> getReviewsForContractor(UUID contractorId) {
        return recommendationRepository.findContractorRecommendationsByContractor_Id(contractorId);
    }
}
