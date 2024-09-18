package com.budgetbuildsystem.service.contractor;

import com.budgetbuildsystem.model.Contractor;

import java.util.List;
import java.util.Optional;

public interface IContractorService {
    void saveContractor(Contractor contractor);

    Optional<Contractor> findByName(String contractorName);


    List<Contractor> findAllContractors();

}

