package com.example.friendmangerment.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InValidEmailException extends Exception{
    private static final long serialVersionUID = 1L;
    public static final String CODE = "400";
    public InValidEmailException() {
        super(CODE);
    }
}
