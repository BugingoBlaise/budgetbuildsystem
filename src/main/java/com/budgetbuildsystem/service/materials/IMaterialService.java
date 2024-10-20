package com.budgetbuildsystem.service.materials;

import com.budgetbuildsystem.model.Materials;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMaterialService {
    void deleteMaterial(UUID materialId);
    public List<Materials> findAllMaterials();
    Optional<Materials> findMaterialById(UUID materialId);
    Materials updateMaterial(Materials material);
     Materials addMaterial(Materials materials);

}
