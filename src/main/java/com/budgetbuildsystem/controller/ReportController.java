package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.dto.ContractorPerformanceReport;
import com.budgetbuildsystem.dto.MaterialProcurementReport;
import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Recommendation;
import com.budgetbuildsystem.model.Supplier;
import com.budgetbuildsystem.service.contractor.IContractorService;
import com.budgetbuildsystem.service.materials.IMaterialService;
import com.budgetbuildsystem.service.recommendations.IRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final IMaterialService materialService;
    private final IContractorService contractorService;
    private final IRecommendationService recommendationService;

    @GetMapping("/material-procurement")
    public ResponseEntity<MaterialProcurementReport> getMaterialProcurementReport() {
        long totalMaterials = materialService.getTotalMaterials();
        Map<String, Long> mostFrequentMaterials = materialService.getMostFrequentMaterials();
        Map.Entry<Supplier, Long> topSupplierEntry = materialService.getSupplierWithMostMaterials();
        Supplier topSupplier = topSupplierEntry.getKey();
        long numberOfMaterials = topSupplierEntry.getValue();
        MaterialProcurementReport report = new MaterialProcurementReport(
                totalMaterials,
                mostFrequentMaterials,
                topSupplier,
                numberOfMaterials
        );
        return ResponseEntity.ok(report);
    }
    @GetMapping("/contractor-performance")
    public ResponseEntity<ContractorPerformanceReport> getContractorPerformanceReport() {
        long totalContractors = contractorService.getTotalContractors();
        double averageRating = contractorService.calculateAverageRating();
        List<Contractor> topContractors = contractorService.findTopContractors();
        List<Recommendation> reviews = contractorService.findAllReviews();
        long totalReviews = contractorService.getTotalReviews();
        ContractorPerformanceReport report = new ContractorPerformanceReport(
                totalContractors,
                averageRating,
                topContractors,
                totalReviews
        );
        return ResponseEntity.ok(report);
    }
}
