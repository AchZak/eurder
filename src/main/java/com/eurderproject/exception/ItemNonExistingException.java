package com.eurderproject.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemNonExistingException extends RuntimeException {
    public ItemNonExistingException(String message) {
        super(message);
    }
}
