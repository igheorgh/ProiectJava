package com.java.proiect.controller;


import com.java.proiect.dto.ErrorDto;
import com.java.proiect.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    protected ResponseEntity<ErrorDto> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .code(404)
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                ErrorDto.builder()
                        .code(400)
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<String>handleRuntimeException(RuntimeException exception)
    {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
