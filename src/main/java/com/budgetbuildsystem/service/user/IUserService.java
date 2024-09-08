package com.budgetbuildsystem.service.user;

import com.budgetbuildsystem.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserService  {
   void saveUser(User user);
   Optional<User>findByUser(String username);

   Citizen saveCitizen(Citizen citizen);
    Page<RHA_Administrator> getAllAdminUsers(Pageable pageable);


  /* User saveContractor(Local_Contractor contractor);
   User signUpSupplier(Supplier supplier);*/
}
