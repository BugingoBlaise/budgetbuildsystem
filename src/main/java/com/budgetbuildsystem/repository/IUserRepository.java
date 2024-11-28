package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Supplier;
import com.budgetbuildsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository  extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);


}
