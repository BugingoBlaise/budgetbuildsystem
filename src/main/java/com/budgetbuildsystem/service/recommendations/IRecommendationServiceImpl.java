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

    public Recommendation rateAndComment(
            UUID contractorId,
            List<String> reviews,
            int rating,
            UUID citizenId
    ) {
        // Retrieve contractor and citizen, throwing an exception if not found
        Contractor contractor = contractorRepository.findById(contractorId)
                .orElseThrow(() -> new EntityNotFoundException("Contractor not found with ID: " + contractorId));
        Citizen citizen = citizenService.getCitizenById(citizenId)
                .orElseThrow(() -> new EntityNotFoundException("Citizen not found with ID: " + citizenId));

        // Create and populate a new Recommendation
        Recommendation newReview = new Recommendation();
        newReview.setRating(rating);
        newReview.setCitizen(citizen);
        newReview.setContractor(contractor);
        newReview.setDate(new Date());
        newReview.setLikeCount(newReview.getLikeCount() + 1);
        newReview.setReviews(reviews);

        // Save the new Recommendation
        Recommendation savedReview = recommendationRepository.save(newReview);

        // Add the saved review to the contractor's review set
        contractor.getReview().add(savedReview);

        // Calculate the average rating
        double averageRating = contractor.getReview().stream()
                .mapToInt(Recommendation::getRating)
                .average()
                .orElse(0.0);

        // Update the contractor's average rating
        contractor.setAverageRating((float) averageRating);
        contractorRepository.save(contractor);

        return savedReview;
    }




    public List<Recommendation> updateAverageRatingForContractor(UUID contractorId) {
        List<Recommendation> reviews = recommendationRepository.findContractorRecommendationsByContractor_Id(contractorId);

        // Calculate average rating
        double averageRating = reviews.stream()
                .mapToInt(Recommendation::getRating)
                .average()
                .orElse(0.0);

        // Retrieve the contractor and update the rating
        contractorRepository.findById(contractorId).ifPresent(contractor -> {
            contractor.setAverageRating((float) averageRating);
            contractorRepository.save(contractor);
        });

        return reviews;
    }


    public List<Recommendation> getReviewsForContractor(UUID contractorId) {
        return recommendationRepository.findContractorRecommendationsByContractor_Id(contractorId);
    }
}
