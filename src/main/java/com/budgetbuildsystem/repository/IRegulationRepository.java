package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.BuildingRegulations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRegulationRepository extends JpaRepository<BuildingRegulations, UUID> {
}
