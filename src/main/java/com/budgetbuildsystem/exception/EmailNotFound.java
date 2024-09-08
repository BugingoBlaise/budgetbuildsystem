package com.budgetbuildsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmailNotFound extends Exception{
    public EmailNotFound(String message) {
        super(message);
    }
}
