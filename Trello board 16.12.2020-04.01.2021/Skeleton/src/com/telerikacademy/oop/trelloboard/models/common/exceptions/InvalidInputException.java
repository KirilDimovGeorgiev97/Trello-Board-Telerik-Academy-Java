package com.telerikacademy.oop.trelloboard.models.common.exceptions;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message){
        super(message);
    }

    public InvalidInputException(){
        this("Invalid input");
    }
}
