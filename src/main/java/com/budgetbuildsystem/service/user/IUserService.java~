package com.budgetbuildsystem.service.user;

import com.budgetbuildsystem.dto.SignDto;
import com.budgetbuildsystem.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    User signUpUser(SignDto signDto);
    void deleteUser(UUID id);

    Optional<User> findByUser(String username);
    Optional<User>findById(UUID id);

    Citizen saveCitizen(Citizen citizen);

    Page<Administrator> getAllAdminUsers(Pageable pageable);

    List<User> findAll();

    List<Contractor> getContractors();

    List<Supplier> loadSuppliers();


    long getTotalUsers();

}
