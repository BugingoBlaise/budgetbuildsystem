package com.budgetbuildsystem.service.citizen;

import com.budgetbuildsystem.exception.EmailNotFound;
import com.budgetbuildsystem.model.Citizen;

import java.util.Optional;
import java.util.UUID;

public interface ICitizenService {
    Citizen saveCitizen(Citizen citizen);
    Optional<Citizen> getCitizenById(UUID id)  ;
    Optional<Citizen> findCitizenByEmail(String email) throws EmailNotFound;
}
