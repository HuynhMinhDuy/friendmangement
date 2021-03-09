package com.example.friendmangerment.service;

import com.example.friendmangerment.dao.PersonRepository;
import com.example.friendmangerment.exception.InValidEmailException;
import com.example.friendmangerment.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
public class PersonServiceTest {

    @TestConfiguration
    public static class PersonServiceConfiguration{
        @Bean
        PersonService personService(){
            return new PersonServiceImp();
        }
    }

    @MockBean
    PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Before
    public void setUp() {
        Person  alex = new Person(1,"alex@gmail.com");
        Mockito.when(personRepository.findByEmail(alex.getEmail()))
                .thenReturn(alex);
    }


    @Test
    public void testResgiter() throws InValidEmailException {
        Person person = personRepository.findByEmail("tom@gmail.com");
        Map<String, Boolean> res = personService.register(person);
        Assert.assertEquals(res.get("status"),true);
    }


}
