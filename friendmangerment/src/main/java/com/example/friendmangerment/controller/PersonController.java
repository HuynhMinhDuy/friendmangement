package com.example.friendmangerment.controller;

import com.example.friendmangerment.dao.PersonRepository;
import com.example.friendmangerment.exception.InValidEmailException;
import com.example.friendmangerment.exception.ResourceAlreadyExist;
import com.example.friendmangerment.model.Person;
import com.example.friendmangerment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    @Autowired
    PersonService personService;


    @PostMapping("/register")
    public Map<String, Boolean> createPerson(@RequestBody Person person) throws ResourceAlreadyExist, InValidEmailException {
        return personService.register(person);
    }

}
