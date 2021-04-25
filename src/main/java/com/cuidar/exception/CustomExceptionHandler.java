package com.cuidar.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@SuppressWarnings({"all"})
public class CustomExceptionHandler {

    @ExceptionHandler(DomainValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIExceptionResponse onDomainValidationException(DomainValidationException domainValidationException) {
        
        APIExceptionResponse response = new APIExceptionResponse("Erro na validação dos dados");
        
        for (String validationMessage : domainValidationException.getValidations()) {
            response.getErrorList().add(new ErrorDetail(validationMessage, ""));
        }

        return response;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public APIExceptionResponse onResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        
        APIExceptionResponse response = new APIExceptionResponse("Recurso não encontrado");
        
        response.getErrorList().add(new ErrorDetail(resourceNotFoundException.getResourceName(), resourceNotFoundException.getClass().toString()));

        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIExceptionResponse onMethodArgumentNotValidException(MethodArgumentNotValidException methodArgNotValidExc) {
        APIExceptionResponse response = new APIExceptionResponse("Argumentos inválidos");

        for (FieldError fieldError : methodArgNotValidExc.getBindingResult().getFieldErrors()) {
            response.getErrorList().add(new ErrorDetail(fieldError.getDefaultMessage(), fieldError.getField()));
        }
        return response;
    }

}
