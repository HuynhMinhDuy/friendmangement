package com.example.friendmangerment.service;

import com.example.friendmangerment.exception.InValidEmailException;
import com.example.friendmangerment.exception.ResourceAlreadyExist;
import com.example.friendmangerment.model.Person;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface PersonService {
    Map<String, Boolean> register(Person peson) throws ResourceAlreadyExist, InValidEmailException;
}
