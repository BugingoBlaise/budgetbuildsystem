package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Materials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMaterialRepository extends JpaRepository<Materials, UUID> {
    @Query("SELECT m FROM Materials m WHERE m.supplier.id = :supplierId")
    Optional<List<Materials>> findMaterialsBySupplierId(@Param("supplierId") UUID supplierId);
}
