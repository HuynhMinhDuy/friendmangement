package com.example.friendmangerment.dao;


import com.example.friendmangerment.model.Person;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testPersonRepository() {
        Person existPerson = new Person(1,"fakeemail@gmail.com");
        Person notFoundPerson = new Person(2,"notfound@gmail.com");
        personRepository.save(existPerson);
        Assertions.assertThat(personRepository.findByEmail(existPerson.getEmail())).isEqualTo(existPerson);
        Assertions.assertThat(personRepository.findByEmail(notFoundPerson.getEmail())).isNull();
        Assertions.assertThat(personRepository.existsByEmail(existPerson.getEmail())).isTrue();
        Assertions.assertThat(personRepository.existsByEmail(notFoundPerson.getEmail())).isFalse();
    }

}
