package com.budgetbuildsystem.model.testModels;

import com.budgetbuildsystem.model.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@MappedSuperclass
 public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID   accountId;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Lob
    private byte[] profilePic;

}
