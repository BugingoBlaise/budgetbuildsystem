package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IAdminRepository extends JpaRepository<Administrator, UUID> {
    Optional<Administrator> findByUsername(String username);
    boolean existsByUsername(String username);
}
