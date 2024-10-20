package com.budgetbuildsystem.service.contractor;

import com.budgetbuildsystem.model.Contractor;

import java.util.List;
import java.util.Optional;

public interface IContractorService {
    void saveContractor(Contractor contractor);

    Optional<Contractor> findByName(String contractorName);

    Optional<Contractor> findContractorByEmailAndUsername(String email, String username);

    List<Contractor> findAllContractors();


}

