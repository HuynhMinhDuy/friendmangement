package com.example.friendmangerment.exception;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiError {
    private String error;
    private int status;
}
