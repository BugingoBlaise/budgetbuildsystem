package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.dto.MaterialProcurementReport;
import com.budgetbuildsystem.model.Supplier;
import com.budgetbuildsystem.service.materials.IMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final IMaterialService materialService;

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
}
