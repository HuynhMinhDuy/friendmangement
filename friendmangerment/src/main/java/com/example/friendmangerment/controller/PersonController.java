package com.example.friendmangerment.controller;

import com.example.friendmangerment.dao.PersonRepository;
import com.example.friendmangerment.exception.InValidEmailException;
import com.example.friendmangerment.exception.ResourceAlreadyExist;
import com.example.friendmangerment.model.Person;
import com.example.friendmangerment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/register")
    public Person createPerson( @RequestBody Person person) throws ResourceAlreadyExist, InValidEmailException {
        return personService.register(person);
    }

}
