package com.budgetbuildsystem.dto;

import com.budgetbuildsystem.model.Contractor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class ContractorPerformanceReport {
    private long totalContractors;
    private double averageRating;
    private List<Contractor> topContractors;
    private long totalReviews;
}



