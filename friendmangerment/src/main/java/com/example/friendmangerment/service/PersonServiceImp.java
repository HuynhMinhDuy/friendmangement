package com.example.friendmangerment.service;


import com.example.friendmangerment.dao.PersonRepository;
import com.example.friendmangerment.exception.InValidEmailException;
import com.example.friendmangerment.exception.ResourceAlreadyExist;
import com.example.friendmangerment.model.Person;
import com.example.friendmangerment.validation.ValidateEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImp implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person register(Person person) throws ResourceAlreadyExist, InValidEmailException {
        if(personRepository.existsByEmail(person.getEmail())) {
                throw new ResourceAlreadyExist();
        }
        ValidateEmail vailidateEmail = new ValidateEmail();
        if(vailidateEmail.validaEmail(person.getEmail()) == false) {
            throw new InValidEmailException();
        }
        return personRepository.save(person);
    }
}
