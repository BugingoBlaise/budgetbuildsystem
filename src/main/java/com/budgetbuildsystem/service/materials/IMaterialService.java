package com.budgetbuildsystem.service.materials;

import com.budgetbuildsystem.model.Materials;
import com.budgetbuildsystem.model.Supplier;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface IMaterialService {
    void deleteMaterial(UUID materialId);
    public List<Materials> findAllMaterials();
    Optional<Materials> findMaterialById(UUID materialId);
    Materials updateMaterial(Materials material);
     Materials addMaterial(Materials materials);
    List<Materials> getMaterialsBySupplierId(UUID supplierId);

    List<Materials>searchMaterialByName(String materialName);


    long getTotalMaterials();

    // New methods for Material Procurement Report
    Map<String, Long> getMostFrequentMaterials();
    Map.Entry<Supplier, Long> getSupplierWithMostMaterials();
}
