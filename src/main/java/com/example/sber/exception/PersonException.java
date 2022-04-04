package com.example.sber.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonException extends RuntimeException {
    public PersonException(String message) {
        super(message);
    }
}
