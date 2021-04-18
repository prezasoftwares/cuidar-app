package com.cuidar.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIExceptionResponse {
    private String title;

    private List<String> messages = new ArrayList<>();

    public APIExceptionResponse(String title) {
        this.title = title;
    }
}