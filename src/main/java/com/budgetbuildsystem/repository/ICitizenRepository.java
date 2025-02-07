package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ICitizenRepository extends JpaRepository<Citizen, UUID> {
    Optional<Citizen> findCitizenByEmail(String email);
    Optional<Citizen>findCitizenByUserId(UUID id);
}
