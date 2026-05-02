package com.exemplo.rest.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("Usuario com id " + id + " não encontrado");
    }
}