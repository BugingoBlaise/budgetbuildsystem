package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Regulations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRegulationRepository extends JpaRepository<Regulations, UUID> {

}
