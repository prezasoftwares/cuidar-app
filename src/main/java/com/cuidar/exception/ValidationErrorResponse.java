package com.cuidar.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorResponse {
    private String message;

    private List<Violation> violations = new ArrayList<>();

    public ValidationErrorResponse(String message) {
        this.message = message;
    }
}