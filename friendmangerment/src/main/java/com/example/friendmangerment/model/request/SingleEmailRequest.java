package com.example.friendmangerment.model.request;


import lombok.Data;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class SingleEmailRequest {
    @Email
    @NotNull
    private String email;
}
