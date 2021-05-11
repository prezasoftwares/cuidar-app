package com.cuidar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserRegisterSecretException extends Exception{

    public UserRegisterSecretException(String message) {
        super(message);
    }
    
}
