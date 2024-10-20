package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Materials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IMaterialRepository extends JpaRepository<Materials, UUID> {
}
