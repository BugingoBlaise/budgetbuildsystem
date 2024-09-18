package com.budgetbuildsystem.service.contractor;

import com.budgetbuildsystem.model.Contractor;
import com.budgetbuildsystem.repository.IContractorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class ContractorServiceImpl implements IContractorService {
    @Autowired
    IContractorRepository repo;

    @Override
    public void saveContractor(Contractor contractor) {
        try {
            Contractor contractor1 = new Contractor();
            contractor1.setCompanyName(contractor.getCompanyName());
            contractor1.setLicenseNumber(contractor.getLicenseNumber());
            contractor1.setContactDetails(contractor.getContactDetails());
            repo.save(contractor1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save contractor");
        }
    }

    @Override
    public Optional<Contractor> findByName(String contractorName) {
        return Optional.ofNullable(repo.findByCompanyName(contractorName).orElseThrow(() -> new EntityNotFoundException("Contractor not found")));
    }

    @Override
    public List<Contractor> findAllContractors() {
        return repo.findAll();
    }
}
