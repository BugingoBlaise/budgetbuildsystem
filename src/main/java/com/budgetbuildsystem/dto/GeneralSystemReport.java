package com.budgetbuildsystem.dto;

import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.model.Materials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GeneralSystemReport {
    private long totalUsers;
    private long totalCitizens;
    private long totalContractors;
    private long totalSuppliers;
    private long totalMaterials;
    private long totalLoans;
    private long totalRegulations;
    private double averageContractorRating;

    // Period-specific data
    private long totalContractorsInPeriod;
    private long totalMaterialsInPeriod;
    private List<Contractor> topContractors;
    private List<Materials> recentMaterials;
}