package com.budgetbuildsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserTypeNotFound extends Exception {
    public UserTypeNotFound(String message) {
        super(message);
    }
}
