package com.budgetbuildsystem.service.materials;

import com.budgetbuildsystem.model.Materials;
import com.budgetbuildsystem.model.Supplier;

import java.util.*;

public interface IMaterialService {
    void deleteMaterial(UUID materialId);
    public List<Materials> findAllMaterials();
    Optional<Materials> findMaterialById(UUID materialId);
    Materials updateMaterial(Materials material);
     Materials addMaterial(Materials materials);
    List<Materials> getMaterialsBySupplierId(UUID supplierId);

    List<Materials> searchMaterialByName(String materialName);


    long getTotalMaterials(Date startDate, Date endDate);

    // New methods for Material Procurement Report
    Map<String, Long> getMostFrequentMaterials(Date startDate, Date endDate);
    Map.Entry<Supplier, Long> getSupplierWithMostMaterials(Date startDate, Date endDate);

    List<Materials> getMaterialsInDateRange(Date startDate, Date endDate);


//    MaterialServiceImpl.SupplierMaterialResult getSupplierWithMostMaterials(Date startDate, Date endDate);
}
