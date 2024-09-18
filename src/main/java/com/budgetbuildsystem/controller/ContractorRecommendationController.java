package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.exception.EmailNotFound;
import com.budgetbuildsystem.model.Recommendation;
import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.service.contractor.IContractorService;
import com.budgetbuildsystem.service.recommendations.IRecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/contractors")
public class ContractorRecommendationController {
    private final IRecommendationService recommendationService;
    private final IContractorService contractorService;

    public ContractorRecommendationController(IRecommendationService recommendationService,IContractorService contractorService) {
        this.recommendationService = recommendationService;
        this.contractorService = contractorService;
    }

    /*@GetMapping("/{contractorId}/reviews")
    public ResponseEntity<List<ContractorRecommendation>> getReviewsForContractor(@PathVariable UUID contractorId) {
        try {
            List<ContractorRecommendation> reviews = recommendationService.getReviewsForContractor(contractorId);
            return ResponseEntity.status(HttpStatus.FOUND).body(reviews);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{contractorId}/reviews")
    public ResponseEntity<ContractorRecommendation> addReview(@PathVariable UUID contractorId, @RequestBody ContractorRecommendation review) {
        try {
            ContractorRecommendation savedReview = recommendationService.addReview(contractorId, review);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{contractorId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable UUID contractorId) {
        try {
        double averageRating = recommendationService.calculateAverageRating(contractorId);
        return ResponseEntity.status(HttpStatus.FOUND).body(averageRating);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/

    @GetMapping
    public ResponseEntity<List<Contractor>> listAllContractors() {
        List<Contractor> contractors = contractorService.findAllContractors();
        return ResponseEntity.ok(contractors);
    }

    @GetMapping("/{contractorId}")
    public ResponseEntity<Optional<Contractor>> getContractorById(@PathVariable UUID contractorId) {
        Optional<Contractor> contractor = recommendationService.getContractorById(contractorId);
        return ResponseEntity.ok(contractor);
    }

    @PostMapping("/{contractorId}/review")
    public ResponseEntity<Recommendation> rateAndComment(
            @PathVariable UUID contractorId,
            @RequestParam int rating,
            @RequestParam String comment,
            @RequestParam UUID citizenId) throws EmailNotFound {
        try {
            Recommendation review = recommendationService.rateAndComment(contractorId, rating, comment, citizenId);
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        } catch (Exception ex) {
            throw new EmailNotFound("Email not found");
        }
    }

    @GetMapping("/{contractorId}/reviews")
    public ResponseEntity<List<Recommendation>> getReviewsForContractor(@PathVariable UUID contractorId) {
        try {
            List<Recommendation> reviews = recommendationService.getReviewsForContractor(contractorId);
            return ResponseEntity.ok(reviews);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{contractorId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable UUID contractorId) {
        try {
            double averageRating = recommendationService.calculateAverageRating(contractorId);
            return ResponseEntity.status(HttpStatus.FOUND).body(averageRating);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
