package com.budgetbuildsystem.service.citizen;

import com.budgetbuildsystem.exception.EmailNotFound;
import com.budgetbuildsystem.model.Citizen;
import com.budgetbuildsystem.repository.ICitizenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CitizenServiceImpl implements ICitizenService {
    @Autowired
    ICitizenRepository citizenRepository;

    @Override
    public Citizen saveCitizen(Citizen citizen) {
        try {
            Citizen newCitizen = new Citizen();
            newCitizen.setFirstName(citizen.getFirstName());
            newCitizen.setLastName(citizen.getLastName());
            newCitizen.setEmail(citizen.getEmail());
//            newCitizen.setUsername(citizen.getUsername());
            newCitizen.setPassword(citizen.getPassword());
            newCitizen.setAddress(citizen.getAddress());
            newCitizen.setPhoneNumber(citizen.getPhoneNumber());
//            newCitizen.setGender(citizen.getGender());
            return citizenRepository.save(citizen);
        }catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Citizen> getCitizenById(UUID id) throws EmailNotFound {
        try {
            return citizenRepository.findById(id);
        } catch (Exception e) {
            throw new EmailNotFound("Email not found");
        }
    }

    @Override
    public Optional<Citizen> findCitizenByEmail(String email) throws EmailNotFound {
        try {
            return citizenRepository.findCitizenByEmail(email);
        } catch (Exception e) {
            throw new EmailNotFound("Email not found");
        }
    }

}
