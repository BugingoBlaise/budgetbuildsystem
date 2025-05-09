package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.dto.ContractorPerformanceReport;
import com.budgetbuildsystem.dto.GeneralSystemReport;
import com.budgetbuildsystem.dto.MaterialProcurementReport;
import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Materials;
import com.budgetbuildsystem.model.Supplier;
import com.budgetbuildsystem.service.citizen.ICitizenService;
import com.budgetbuildsystem.service.contractor.IContractorService;
import com.budgetbuildsystem.service.loan.ILoanService;
import com.budgetbuildsystem.service.materials.IMaterialService;
import com.budgetbuildsystem.service.recommendations.IRecommendationService;
import com.budgetbuildsystem.service.regulations.IRegulationsService;
import com.budgetbuildsystem.service.supplier.ISupplierService;
import com.budgetbuildsystem.service.user.IUserService;
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
    private final IUserService userService;
    private final ICitizenService citizenService;
    private final ILoanService loanService;
    private final ISupplierService supplierService;
    private final IRegulationsService regulationService;

    @GetMapping("/material-procurement")
    public ResponseEntity<MaterialProcurementReport> getMaterialProcurementReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        long totalMaterials = materialService.getTotalMaterials(startDate, endDate);
        Map<String, Long> mostFrequentMaterials = materialService.getMostFrequentMaterials(startDate, endDate);
        Map.Entry<Supplier, Long> topSupplierEntry = materialService.getSupplierWithMostMaterials(startDate, endDate);
        List<Materials> materialsInRange = materialService.getMaterialsInDateRange(startDate, endDate);

        MaterialProcurementReport report;

        if (topSupplierEntry.getKey() != null && !topSupplierEntry.getKey().getCompanyName().equals("No supplier found")) {
            report = new MaterialProcurementReport(
                    totalMaterials,
                    mostFrequentMaterials,
                    topSupplierEntry.getKey(),
                    topSupplierEntry.getValue(),
                    materialsInRange
            );
        } else {
            report = MaterialProcurementReport.withNoSupplier(
                    totalMaterials,
                    mostFrequentMaterials,
                    materialsInRange
            );
        }

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

    @GetMapping("/general-system")
    public ResponseEntity<GeneralSystemReport> getGeneralSystemReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        long totalUsers = userService.getTotalUsers();
        long totalCitizens = citizenService.getTotalCitizens();
        long totalContractors = contractorService.countContractors();
        long totalSuppliers = supplierService.getTotalSuppliers();
        long totalMaterials = materialService.getTotalMaterials(startDate, endDate);
        long totalLoans = loanService.getTotalLoans();
        long totalRegulations = regulationService.getTotalRegulations();
        double averageContractorRating = contractorService.getAverageContractorRating();

        // Get additional data for the report
        long totalContractorsInPeriod = contractorService.getTotalContractors(startDate, endDate);
        long totalMaterialsInPeriod = materialService.getTotalMaterials(startDate, endDate);
        List<Contractor> topContractors = contractorService.findTopContractors(startDate, endDate);
        List<Materials> recentMaterials = materialService.getMaterialsInDateRange(startDate, endDate);

        GeneralSystemReport report = new GeneralSystemReport(
                totalUsers,
                totalCitizens,
                totalContractors,
                totalSuppliers,
                totalMaterials,
                totalLoans,
                totalRegulations,
                averageContractorRating,
                totalContractorsInPeriod,
                totalMaterialsInPeriod,
                topContractors,
                recentMaterials
        );

        return ResponseEntity.ok(report);
    }
}
