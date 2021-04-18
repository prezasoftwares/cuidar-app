package com.cuidar.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainValidationException extends RuntimeException {
    private List<String> validations = new ArrayList<>();

    public DomainValidationException(String message) {
        super(message);
    }

    public void addMessage(String message){
        this.validations.add(message);
    }
}