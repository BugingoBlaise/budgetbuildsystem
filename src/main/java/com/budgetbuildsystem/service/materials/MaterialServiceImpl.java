package com.budgetbuildsystem.service.materials;

import com.budgetbuildsystem.model.Materials;
import com.budgetbuildsystem.model.Supplier;
import com.budgetbuildsystem.repository.IMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MaterialServiceImpl implements IMaterialService {
    private final IMaterialRepository repository;

    @Override
    public void deleteMaterial(UUID materialId) {
        repository.deleteById(materialId);
    }

    @Override
    public List<Materials> findAllMaterials() {
        return repository.findAll();
    }

    @Override
    public Optional<Materials> findMaterialById(UUID materialId) {
        return Optional.ofNullable(repository.findById(materialId).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Materials updateMaterial(Materials material) {
        Optional<Materials> checkMaterial = repository.findById(material.getId());
        if (checkMaterial.isPresent()) {
            return repository.save(material);
        } else {
            throw new EntityNotFoundException("Material not found with id: " + material.getId());
        }
    }

    @Override
    public Materials addMaterial(Materials material) {
        if (material.getSupplier() == null) {
            throw new IllegalArgumentException("Supplier must be set for the material");
        }
        return repository.save(material);
    }

    @Override
    public List<Materials> getMaterialsBySupplierId(UUID supplierId) {
        Optional<List<Materials>> materials = repository.findMaterialsBySupplierId(supplierId);
        return materials.orElseThrow(() -> new EntityNotFoundException("No materials found for supplier ID: " + supplierId));
    }

    @Override
    public List<Materials> searchMaterialByName(String materialName) {
        if (materialName == null || materialName.trim().isEmpty()) {
            throw new IllegalArgumentException("Material name cannot be empty");
        }
        Optional<List<Materials>> materials = repository.findMaterialsByMaterialName(materialName.trim());
        return materials.orElseThrow(() -> new EntityNotFoundException("Material not found"));
    }


    @Override
    public long getTotalMaterials() {
        return repository.count();
    }


    // New method: Get most frequent materials
    @Override
    public Map<String, Long> getMostFrequentMaterials() {
        List<Materials> allMaterials = repository.findAll();

        // Group materials by name and count occurrences
        Map<String, Long> materialFrequency = allMaterials.stream().collect(Collectors.groupingBy(Materials::getMaterialName, Collectors.counting()));

        return materialFrequency.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Override
    public Map.Entry<Supplier, Long> getSupplierWithMostMaterials() {
        List<Materials> allMaterials = repository.findAll();
        // Group materials by supplier and count occurrences
        Map<Supplier, Long> supplierMaterialCount = allMaterials.stream()
                .collect(Collectors.groupingBy(Materials::getSupplier, Collectors.counting()));

        // Find the supplier with the maximum materials
        return supplierMaterialCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new EntityNotFoundException("No suppliers found"));
    }
}
