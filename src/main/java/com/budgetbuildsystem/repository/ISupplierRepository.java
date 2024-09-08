package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ISupplierRepository extends JpaRepository<Supplier, UUID> {
   Optional<Supplier>findBySupplierName(String supplierName);
}
