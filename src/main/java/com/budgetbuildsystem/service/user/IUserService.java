package com.budgetbuildsystem.service.user;

import com.budgetbuildsystem.dto.SignDto;
import com.budgetbuildsystem.model.Administrator;
import com.budgetbuildsystem.model.Citizen;
import com.budgetbuildsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    User signUpUser(SignDto signDto);

    Optional<User> findByUser(String username);
    Optional<User>findById(UUID id);

    Citizen saveCitizen(Citizen citizen);

    Page<Administrator> getAllAdminUsers(Pageable pageable);

    List<User> findAll();

  /* User saveContractor(Local_Contractor contractor);
   User signUpSupplier(Supplier supplier);*/
}
