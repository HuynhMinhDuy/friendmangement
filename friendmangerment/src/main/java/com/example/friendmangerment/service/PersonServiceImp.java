package com.example.friendmangerment.service;


import com.example.friendmangerment.dao.PersonRepository;
import com.example.friendmangerment.exception.InValidEmailException;
import com.example.friendmangerment.exception.ResourceAlreadyExist;
import com.example.friendmangerment.model.Person;
import com.example.friendmangerment.validation.ValidateEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Service
public class PersonServiceImp implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Map<String, Boolean> register(Person person) throws ResourceAlreadyExist, InValidEmailException {

        System.out.println("Calling Service here ");

        if(personRepository.existsByEmail(person.getEmail())) {
            System.out.println("test print here");
            throw new ResourceAlreadyExist();
        }
        ValidateEmail vailidateEmail = new ValidateEmail();
        if(vailidateEmail.validaEmail(person.getEmail()) == false) {
            throw new InValidEmailException();
        }
        System.out.println("test print here ???????");
        Map<String, Boolean> result = new HashMap<>();
        personRepository.save(person);
        result.put("status", true);
        return  result;
    }
}
