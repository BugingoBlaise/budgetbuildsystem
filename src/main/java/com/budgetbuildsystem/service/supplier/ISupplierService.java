package com.budgetbuildsystem.service.supplier;

import com.budgetbuildsystem.model.Supplier;

import java.util.Optional;

public interface ISupplierService {
    void saveSupplier(Supplier supplier);
    Optional<Supplier> findSupplierByName(String name);
}
