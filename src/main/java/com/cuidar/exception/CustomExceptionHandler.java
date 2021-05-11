package com.cuidar.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

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

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIExceptionResponse onBadCredentialsException(BadCredentialsException badCredentialsException) {
        APIExceptionResponse response = new APIExceptionResponse("Credenciais inválidas");
    
        response.getErrorList().add(new ErrorDetail(badCredentialsException.getMessage(), ""));
    
        return response;
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIExceptionResponse onSignatureException(SignatureException signatureException) {
        APIExceptionResponse response = new APIExceptionResponse("Token JWT inválido");
    
        response.getErrorList().add(new ErrorDetail(signatureException.getMessage(), signatureException.getLocalizedMessage()));
    
        return response;
    }
    
    @ExceptionHandler(UserEmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIExceptionResponse onUserEmailAlreadyRegisteredException(UserEmailAlreadyRegisteredException userEmailAlreadyRegisteredException) {
        APIExceptionResponse response = new APIExceptionResponse("Email já registrado");
    
        response.getErrorList().add(new ErrorDetail(userEmailAlreadyRegisteredException.getMessage(), userEmailAlreadyRegisteredException.getLocalizedMessage()));
    
        return response;
    }

    @ExceptionHandler(UserRegisterSecretException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIExceptionResponse onUserRegisterSecretException(UserRegisterSecretException userRegisterSecretException) {
        APIExceptionResponse response = new APIExceptionResponse("Erro na chave secreta de registro");
        
        response.getErrorList().add(new ErrorDetail(userRegisterSecretException.getMessage(), userRegisterSecretException.getLocalizedMessage()));
    
        return response;
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIExceptionResponse onMalformedJwtException(MalformedJwtException malformedJwtException){
        APIExceptionResponse response = new APIExceptionResponse("Erro encontrado no token JWT");
        
        response.getErrorList().add(new ErrorDetail(malformedJwtException.getMessage(), malformedJwtException.getLocalizedMessage()));
    
        return response;
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIExceptionResponse onUserAlreadyRegisteredException(UserAlreadyRegisteredException userAlreadyRegisteredException) {
        APIExceptionResponse response = new APIExceptionResponse("Usuário já registrado");
    
        response.getErrorList().add(new ErrorDetail(userAlreadyRegisteredException.getMessage(), userAlreadyRegisteredException.getLocalizedMessage()));
    
        return response;
    }
}
