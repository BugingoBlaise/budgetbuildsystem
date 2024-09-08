package com.budgetbuildsystem.service.regulations;

import com.budgetbuildsystem.model.BuildingRegulations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRegulationsService {
    List<BuildingRegulations> getAllRegulations();

    Optional<BuildingRegulations> getRegulationById(UUID id);

    BuildingRegulations saveRegulation(BuildingRegulations regulation);

    void deleteRegulationById(UUID id);
}
