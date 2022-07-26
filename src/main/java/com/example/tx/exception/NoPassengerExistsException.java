package com.example.tx.exception;

public class NoPassengerExistsException extends RuntimeException{
    private String message;

    public NoPassengerExistsException(){

    }
    public NoPassengerExistsException(String message){
        super(message);
        this.message = message;
    }
}
