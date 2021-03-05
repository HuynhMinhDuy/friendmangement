package com.example.friendmangerment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExist extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public ResourceAlreadyExist() {
                super();
        }
}
