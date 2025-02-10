package com.budgetbuildsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminDashData {
    private long totalUsers;
    private long totalCitizens;
    private long totalContractors;
    private long totalSuppliers;
    private long totalMaterials;
    private long totalLoans;
    private long totalRegulations;
    private double averageContractorRating;
}
