package com.example.website;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    //400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders _headers, HttpStatus _status, WebRequest request) {

        StringBuilder infos = new  StringBuilder();
        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            infos.append("'"+fieldError.getField()+"': "+fieldError.getDefaultMessage()+", ");
        }
        infos.delete(infos.length()-2, infos.length());

        return handleExceptionInternal(
                ex,
                getBodyOfResponse("La requête contient "+ex.getBindingResult().getFieldErrors().size()+" contraintes non respectées : ["+infos.toString()+"]"),
                getHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    // 500 (generic)
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleInternalUnknownError(Exception ex, WebRequest request) {

        return handleExceptionInternal(
                ex,
                getBodyOfResponse(ex.getMessage()),
                getHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    private String getBodyOfResponse(String message)
    {
        return String.format("{\"error\": \"%s\"}", Optional.ofNullable(message).orElse("Unknown error"));
    }

    private HttpHeaders getHeaders()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
