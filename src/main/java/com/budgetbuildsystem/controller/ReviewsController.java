package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Recommendation;
import com.budgetbuildsystem.service.citizen.ICitizenService;
import com.budgetbuildsystem.service.contractor.IContractorService;
import com.budgetbuildsystem.service.recommendations.IRecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/reviews")
public class ReviewsController {
    private final IRecommendationService recommendationService;
    private final ICitizenService citizenService;
    private final IContractorService contractorService;

    public ReviewsController(IRecommendationService recommendationService, ICitizenService citizenService, IContractorService contractorService) {
        this.recommendationService = recommendationService;
        this.citizenService = citizenService;
        this.contractorService = contractorService;
    }

    @GetMapping("/contractors")
    public ResponseEntity<List<Contractor>> listAllContractors() {
        return ResponseEntity.ok(contractorService.findAllContractors());
    }

    @GetMapping("contractors/{contractorId}")
    public ResponseEntity<?> getContractorById(@PathVariable UUID contractorId) {
        try {
            Optional<Contractor> contractor = contractorService.getContractorById(contractorId);
            return contractor.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Contractor  found for ID {}" + contractorId) :
                    ResponseEntity.ok(contractor);
        } catch (Exception ex) {
            log.error("Error fetching contractors for contractor {}: {}", contractorId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving contractors.");
        }
    }

    @PutMapping("/review/{contractorId}")
    public ResponseEntity<?> rateAndComment(
            @PathVariable UUID contractorId,
            @RequestBody Recommendation recommendation,
            @RequestParam("citizenId") String citizenId

    ) {
        try {
            if (recommendation.getReviews() == null || recommendation.getReviews().isEmpty()) {
                return ResponseEntity.badRequest().body("At least one review comment is required.");
            }
            if (recommendation.getRating() <= 0) {
                return ResponseEntity.badRequest().body("A valid rating is required.");
            }
            if (citizenId == null ) {
                return ResponseEntity.badRequest().body("Citizen ID is required.");
            }

            log.info("Rating and commenting contractor with ID {}", contractorId);
            log.info("Rating: {}", recommendation.getRating());
            log.info("Reviews: {}", recommendation.getReviews());
            log.info("Citizen ID: {}", citizenId);


            UUID citId = UUID.fromString(citizenId);

            Recommendation review = recommendationService.rateAndComment(
                    contractorId,
                    recommendation.getReviews(),
                    recommendation.getRating(),
                    citId
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        } catch (Exception ex) {
            log.error("Error occurred while rating contractor with ID {}: {}", contractorId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }

    @GetMapping("/findByContractorId/{contractorId}")
    public ResponseEntity<?> getReviewsForContractor(@PathVariable UUID contractorId) {
        try {
            List<Recommendation> reviews = recommendationService.getReviewsForContractor(contractorId);
            return reviews.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews found") :
                    ResponseEntity.ok(reviews);
        } catch (Exception ex) {
            log.error("Error fetching reviews for contractor {}: {}", contractorId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving reviews.");
        }
    }

}
