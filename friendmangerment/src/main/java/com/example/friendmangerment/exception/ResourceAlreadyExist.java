package com.example.friendmangerment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExist  extends Exception {
        private static final long serialVersionUID = 1L;
        public static final String CODE = "409";
        public ResourceAlreadyExist() {
            super(CODE);
        }
}
