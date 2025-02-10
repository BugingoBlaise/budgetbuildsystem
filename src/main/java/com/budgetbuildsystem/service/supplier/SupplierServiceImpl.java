package com.budgetbuildsystem.service.supplier;

import com.budgetbuildsystem.model.Supplier;
import com.budgetbuildsystem.repository.ISupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Slf4j
@Service
public class SupplierServiceImpl implements ISupplierService {
    @Autowired
    ISupplierRepository supplierRepository;

    @Override
    public void saveSupplier(Supplier supplier) {
        if (supplier != null) {
            supplier.setSupplierName(supplier.getSupplierName());
//            supplier.set(supplier.getContactDetails());
            supplierRepository.save(supplier);
        }
    }

    @Override
    public Optional<Supplier> findSupplierByName(String name) {
        return Optional.ofNullable(supplierRepository.findBySupplierName(name).orElseThrow(() -> new UsernameNotFoundException("Supplier not found")));
    }

    @Override
    public long getTotalSuppliers() {
        return supplierRepository.count();
    }
}
