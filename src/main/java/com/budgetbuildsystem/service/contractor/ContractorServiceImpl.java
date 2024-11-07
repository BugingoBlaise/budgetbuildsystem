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
import java.util.UUID;

@Transactional
@Service
@Slf4j
public class ContractorServiceImpl implements IContractorService {
    @Autowired
    IContractorRepository repo;

    @Override
    public void saveContractor(Contractor contractor) {
        try {
            Optional<Contractor> checkContractor = repo.findContractorByEmailAndUsername(contractor.getEmail(), contractor.getUsername());
            if (checkContractor.isPresent()) {
                Contractor contractor1 = new Contractor();
                contractor1.setCompanyName(contractor.getCompanyName());
                contractor1.setLicenseNumber(contractor.getLicenseNumber());
                contractor1.setContactDetails(contractor.getContactDetails());
                repo.save(contractor1);
            } else {
                throw new RuntimeException("Entity EXISTS E");
            }
        } catch (Exception e) {
            throw new RuntimeException("Internal error exception");
        }
    }

    @Override
    public Optional<Contractor> findByName(String contractorName) {
        return Optional.ofNullable(repo.findByCompanyName(contractorName).orElseThrow(() -> new EntityNotFoundException("Contractor not found")));
    }

    @Override
    public Optional<Contractor> findContractorByEmailAndUsername(String email, String username) {
        return Optional.ofNullable(repo.findContractorByEmailAndUsername(email, username).orElseThrow(EntityNotFoundException::new));
    }


    @Override
    public List<Contractor> findAllContractors() {
        return repo.findAll();
    }

    @Override
    public Optional<Contractor> getContractorById(UUID uuid) {
        return Optional.ofNullable(repo.findById(uuid).orElseThrow(()->new EntityNotFoundException("Contractor of ID : {}"+uuid+ "NOT FOUND")));
    }
}
