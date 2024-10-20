package com.budgetbuildsystem.service.materials;

import com.budgetbuildsystem.model.Materials;
import com.budgetbuildsystem.repository.IMaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service@Slf4j
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
    public Materials addMaterial(Materials materials) {
        try{
            return   repository.save(materials);
        }catch (Exception e){
            throw new RuntimeException("Error while saving material: " + e.getMessage());
        }


    }
}
