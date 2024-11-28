package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Supplier;
import com.budgetbuildsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ISupplierRepository extends JpaRepository<Supplier, UUID> {
   Optional<Supplier> findByUser(User user);
   Optional<Supplier>findBySupplierName(String supplierName);
}
