package com.example.friendmangerment.service;

import com.example.friendmangerment.exception.InValidEmailException;
import com.example.friendmangerment.exception.ResourceAlreadyExist;
import com.example.friendmangerment.model.Person;
import org.springframework.stereotype.Service;


public interface PersonService {
    Person register(Person peson) throws ResourceAlreadyExist, InValidEmailException;
}
