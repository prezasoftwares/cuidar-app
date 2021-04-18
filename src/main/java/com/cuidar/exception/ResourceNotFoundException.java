package com.cuidar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    
    private String resourceName;

    public ResourceNotFoundException(String message, String resourceName) {
        super(message);
        this.resourceName = resourceName;
    }
}