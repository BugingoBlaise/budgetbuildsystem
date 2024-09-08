package com.budgetbuildsystem.service.contractor;

import com.budgetbuildsystem.model.Local_Contractor;

import java.util.List;
import java.util.Optional;

public interface IContractorService {
    void saveContractor(Local_Contractor contractor);

    Optional<Local_Contractor> findByName(String contractorName);


    List<Local_Contractor> findAllContractors();

}

