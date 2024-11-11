package com.budgetbuildsystem.service.recommendations;

import com.budgetbuildsystem.model.Citizen;
import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Recommendation;
import com.budgetbuildsystem.repository.IContractorRepository;
import com.budgetbuildsystem.repository.IRecommendationsRepo;
import com.budgetbuildsystem.service.citizen.ICitizenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class IRecommendationServiceImpl implements IRecommendationService {
    private final IRecommendationsRepo recommendationRepository;
    private final IContractorRepository contractorRepository;
    private final ICitizenService citizenService;

    public List<Contractor> findAllContractors() {
        return contractorRepository.findAll();
    }

    @Override
    public List<Contractor> listAllContractors() {
        return contractorRepository.findAll();
    }

    public Optional<Contractor> getContractorById(UUID contractorId) {
        return contractorRepository.findById(contractorId);
    }

    public Recommendation rateAndComment(UUID contractorId, List<String> reviews, int rating, UUID citizenId) {
        // Fetch Contractor and Citizen, throw if not found
        Contractor contractor = contractorRepository.findById(contractorId)
                .orElseThrow(() -> new EntityNotFoundException("Contractor not found with ID: " + contractorId));
        Citizen citizen = citizenService.getCitizenById(citizenId)
                .orElseThrow(() -> new EntityNotFoundException("Citizen not found with ID: " + citizenId));

        // Create and save the new Recommendation
        Recommendation newReview = new Recommendation();
        newReview.setRating(rating);
        newReview.setCitizen(citizen);
        newReview.setContractor(contractor);
        newReview.setDate(new Date());
        newReview.setLikeCount(1);  // Set initial like count
        newReview.setReviews(reviews);
        Recommendation savedReview = recommendationRepository.save(newReview);
        updateAverageRatingForContractor(contractor);
        return savedReview;
    }


   /* private static Recommendation updateContractorRating(UUID id){
        List<Recommendation> reviews = recommendationRepository.


        // Update the contractor's review list and recalculate average rating
        contractor.getReview().add(savedReview);
        double averageRating = contractor.getReview().stream()
                .mapToInt(Recommendation::getRating)
                .average()
                .orElse(0.0);
        contractor.setAverageRating((float) averageRating);
        contractorRepository.save(contractor);
    }*/


    private void updateAverageRatingForContractor(Contractor contractor) {
        // Retrieve all reviews for the contractor
        List<Recommendation> reviews = findRecommendationsByContractor(contractor);

        // Calculate the average rating or set to 0 if no reviews are present
        double averageRating = reviews.isEmpty() ? 0.0 :
                reviews.stream().mapToInt(Recommendation::getRating).average().orElse(0.0);

        // Update the contractor's average rating directly
        contractor.setAverageRating((float) averageRating);
        contractorRepository.save(contractor);
    }



    @Override
    public List<Recommendation> findRecommendationsByContractor(Contractor contractor) {
        return recommendationRepository.findRecommendationsByContractor(contractor);
    }
}
