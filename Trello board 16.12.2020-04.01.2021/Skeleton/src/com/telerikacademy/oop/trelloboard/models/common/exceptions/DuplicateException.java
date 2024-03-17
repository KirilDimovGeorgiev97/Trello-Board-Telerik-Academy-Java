package com.telerikacademy.oop.trelloboard.models.common.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message){
        super(message);
    }

    public DuplicateException(){
        this("Invalid input");
    }

}
