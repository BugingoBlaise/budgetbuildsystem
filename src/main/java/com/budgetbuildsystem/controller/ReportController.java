package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.dto.ContractorPerformanceReport;
import com.budgetbuildsystem.dto.MaterialProcurementReport;
import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Materials;
import com.budgetbuildsystem.model.Supplier;
import com.budgetbuildsystem.service.contractor.IContractorService;
import com.budgetbuildsystem.service.materials.IMaterialService;
import com.budgetbuildsystem.service.recommendations.IRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
  public ResponseEntity<MaterialProcurementReport> getMaterialProcurementReport(
          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {


      System.out.println("Start date: "+startDate);
      System.out.println("End date: " +endDate);

      long totalMaterials = materialService.getTotalMaterials(startDate, endDate);
      Map<String, Long> mostFrequentMaterials = materialService.getMostFrequentMaterials(startDate, endDate);
      Map.Entry<Supplier, Long> topSupplierEntry = materialService.getSupplierWithMostMaterials(startDate, endDate);

      // Get the actual materials in the date range for the table
      List<Materials> materialsInRange = materialService.getMaterialsInDateRange(startDate, endDate);

      Supplier topSupplier = topSupplierEntry.getKey();
      long numberOfMaterials = topSupplierEntry.getValue();

      MaterialProcurementReport report = new MaterialProcurementReport(
              totalMaterials,
              mostFrequentMaterials,
              topSupplier,
              numberOfMaterials,
              materialsInRange // New field
      );
      return ResponseEntity.ok(report);
  }

    @GetMapping("/contractor-performance")
    public ResponseEntity<ContractorPerformanceReport> getContractorPerformanceReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {


        System.out.println("Start date: "+startDate);
        System.out.println("End date: "+endDate);
        long totalContractors = contractorService.getTotalContractors(startDate, endDate);
        double averageRating = contractorService.calculateAverageRating(startDate, endDate);
        List<Contractor> topContractors = contractorService.findTopContractors(startDate, endDate);
        long totalReviews = contractorService.getTotalReviews(startDate, endDate);

        ContractorPerformanceReport report = new ContractorPerformanceReport(
                totalContractors,
                averageRating,
                topContractors,
                totalReviews
        );
        return ResponseEntity.ok(report);
    }


}
