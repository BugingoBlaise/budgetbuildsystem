package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.exception.BuildingRegulationNotFoundException;
import com.budgetbuildsystem.model.BuildingRegulations;
import com.budgetbuildsystem.service.regulations.IRegulationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/regulations")
public class RegulationController {
    @Autowired
    private IRegulationsService buildingRegulationsService;

    @GetMapping
    public ResponseEntity<List<BuildingRegulations>> getAllRegulations() {
        return ResponseEntity.ok(buildingRegulationsService.getAllRegulations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingRegulations> getRegulationById(@PathVariable UUID id) {
        Optional<BuildingRegulations> regulation = buildingRegulationsService.getRegulationById(id);
        return regulation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?>
    saveRegulation(
            @RequestBody BuildingRegulations regulation
    ) throws IOException {
        try {
            BuildingRegulations newRegulation = new BuildingRegulations();
            newRegulation.setRegulationTitle(regulation.getRegulationTitle());
            newRegulation.setRegulationDetails(regulation.getRegulationDetails());
            newRegulation.setRegulationImage(regulation.getRegulationImage());
            return ResponseEntity.status(HttpStatus.CREATED).body(buildingRegulationsService.saveRegulation(regulation));
        } catch (Exception ex) {
            throw new RuntimeException("Failed to save regulation: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegulationById(@PathVariable UUID id) {
        try {
            buildingRegulationsService.deleteRegulationById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BuildingRegulationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegulation(@PathVariable UUID id, @RequestBody BuildingRegulations updatedRegulation) {
        Optional<BuildingRegulations> existingRegulation = buildingRegulationsService.getRegulationById(id);

        if (existingRegulation.isPresent()) {
            BuildingRegulations regulationToUpdate = existingRegulation.get();
            regulationToUpdate.setRegulationTitle(updatedRegulation.getRegulationTitle());
            regulationToUpdate.setRegulationDetails(updatedRegulation.getRegulationDetails());
            regulationToUpdate.setRegulationImage(updatedRegulation.getRegulationImage());

            regulationToUpdate = buildingRegulationsService.saveRegulation(regulationToUpdate);
            return ResponseEntity.ok(regulationToUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}