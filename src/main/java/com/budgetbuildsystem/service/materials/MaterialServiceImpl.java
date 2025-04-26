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
        return repository.findMaterialsBySupplierId(supplierId);

    }

    @Override
    public List<Materials> searchMaterialByName(String materialName) {
        if (materialName == null || materialName.trim().isEmpty()) {
            throw new IllegalArgumentException("Material name cannot be empty");
        }
        return repository.findMaterialsByMaterialName(materialName.trim());
    }


    @Override
    public long getTotalMaterials(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return repository.count();
        }
        return repository.countByPostedDateBetween(startDate, endDate);
    }

    @Override
    public Map<String, Long> getMostFrequentMaterials(Date startDate, Date endDate) {
        List<Materials> materials;
        if (startDate == null && endDate == null) {
            materials = repository.findAll();
        } else {
            materials = repository.findByPostedDateBetween(startDate, endDate);
        }

        // Group materials by name and count occurrences
        Map<String, Long> materialFrequency = materials.stream()
                .collect(Collectors.groupingBy(Materials::getMaterialName, Collectors.counting()));

        return materialFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }




    @Override
    public Map.Entry<Supplier, Long> getSupplierWithMostMaterials(Date startDate, Date endDate) {
        List<Materials> materials = (startDate == null && endDate == null)
                ? repository.findAll()
                : repository.findByPostedDateBetween(startDate, endDate);

        // Create a default empty supplier if needed
        Supplier emptySupplier = new Supplier();
        emptySupplier.setCompanyName("No supplier found");

        return materials.stream()
                .collect(Collectors.groupingBy(
                        Materials::getSupplier,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleEntry<>(emptySupplier, 0L));
    }

    @Override
    public List<Materials> getMaterialsInDateRange(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return repository.findAll();
        }
        return repository.findByPostedDateBetween(startDate, endDate);
    }






}
