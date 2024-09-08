package com.budgetbuildsystem.service.recommendationsAndReviews;

import com.budgetbuildsystem.exception.EmailNotFound;
import com.budgetbuildsystem.model.Citizen;
import com.budgetbuildsystem.model.ContractorRecommendation;
import com.budgetbuildsystem.model.Local_Contractor;
import com.budgetbuildsystem.repository.IContractorRepository;
import com.budgetbuildsystem.repository.IRecommendationsRepo;
import com.budgetbuildsystem.service.citizen.ICitizenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Transactional
@Service
public class IRecommendationServiceImpl implements IRecommendationService {
    private final IRecommendationsRepo recommendationRepository;
    private final IContractorRepository contractorRepository;
    private final ICitizenService citizenService;

    public IRecommendationServiceImpl(IRecommendationsRepo recommendationRepository, IContractorRepository contractorRepository, ICitizenService citizenService) {
        this.recommendationRepository = recommendationRepository;
        this.contractorRepository = contractorRepository;
        this.citizenService = citizenService;
    }

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
    public List<Local_Contractor> listAllContractors() {
        return contractorRepository.findAll();
    }

    // Get contractor details by ID
    public Optional<Local_Contractor> getContractorById(UUID contractorId) {
        return contractorRepository.findById(contractorId);
    }

    // Rate and comment on a contractor
    public ContractorRecommendation rateAndComment(UUID contractorId, int rating, String comment, UUID citizenId) throws EmailNotFound {
        Optional<Local_Contractor> contractorOptional = contractorRepository.findById(contractorId);
        Optional<Citizen> citizen = citizenService.getCitizenById(citizenId);
        if (contractorOptional.isPresent()) {
            Local_Contractor contractor = contractorOptional.get();
            ContractorRecommendation newReview = new ContractorRecommendation();
            newReview.setRating(rating);
            newReview.setReviews(Collections.singletonList(comment));
            newReview.setContractor(contractor);
            citizen.ifPresent(newReview::setCitizen);// Assuming Citizen ID is passed in for reference
            newReview.setDate(new java.util.Date());
            newReview.setLikeCount(0);
            // Save the new review
            ContractorRecommendation savedReview = recommendationRepository.save(newReview);
            // Update the contractor's reviews list and average rating
            contractor.getReviews().add(savedReview);
            updateContractorRating(contractor);
            return savedReview;
        } else {
            throw new RuntimeException("Contractor not found with ID: " + contractorId);
        }
    }

    // Calculate and update the contractor's average rating
    public void updateContractorRating(Local_Contractor contractor) {
        double averageRating = calculateAverageRating(contractor.getId());
        contractor.setAverageRating(averageRating);
        contractorRepository.save(contractor);
    }

    // Calculate the average rating for a contractor
    public double calculateAverageRating(UUID contractorId) {
        List<ContractorRecommendation> reviews = recommendationRepository.findContractorRecommendationsByContractor_Id(contractorId);
        return reviews.stream()
                .mapToInt(ContractorRecommendation::getRating)
                .average()
                .orElse(0.0);
    }

    // Get all reviews for a contractor
    public List<ContractorRecommendation> getReviewsForContractor(UUID contractorId) {
        return recommendationRepository.findContractorRecommendationsByContractor_Id(contractorId);
    }
}
