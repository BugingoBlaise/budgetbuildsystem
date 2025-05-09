package com.budgetbuildsystem.dto;

import com.budgetbuildsystem.model.Materials;
import com.budgetbuildsystem.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class MaterialProcurementReport {
    private long totalMaterials;
    private Map<String, Long> mostFrequentMaterials;
    private Supplier topSupplier;
    private long numberOfMaterials;
    private List<Materials> materialsInRange;


    // Add a static method for empty supplier case
    public static MaterialProcurementReport withNoSupplier(
            long totalMaterials,
            Map<String, Long> mostFrequentMaterials,
            List<Materials> materialsInRange) {
        return new MaterialProcurementReport(
                totalMaterials,
                mostFrequentMaterials,
                null, // null supplier
                0,    // 0 materials
                materialsInRange
        );
    }
}
