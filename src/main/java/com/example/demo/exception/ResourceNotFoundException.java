package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String recurso, Long id) {
        super(String.format("%s não encontrado(a) com id: %d", recurso, id));
    }

    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }
}