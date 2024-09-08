package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.RHA_Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAdminRepository extends JpaRepository<RHA_Administrator, UUID> {
}
