package com.budgetbuildsystem.service.contractor;

import com.budgetbuildsystem.model.Contractor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IContractorService {
    void saveContractor(Contractor contractor);

    Optional<Contractor> findByName(String contractorName);

    Optional<Contractor> findContractorByEmailAndUsername(String email, String username);

    List<Contractor> findAllContractors();
    Optional<Contractor>getContractorById(UUID uuid);

    double getAverageContractorRating();
    long countContractors();


}

