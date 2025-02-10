package com.budgetbuildsystem.service.regulations;

import com.budgetbuildsystem.model.Regulations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRegulationsService {
    List<Regulations> getAllRegulations();

    Optional<Regulations> getRegulationById(UUID id);

    Regulations saveRegulation(Regulations regulation);

    void deleteRegulationById(UUID id);


    long getTotalRegulations();
}
