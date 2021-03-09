package com.example.friendmangerment.dao;


import com.example.friendmangerment.model.FriendManagement;
import com.example.friendmangerment.model.Person;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FriendRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testFriendRepository() {
        Person person1 = new Person(1,"jonh@gmail.com");
        Person person2 = new Person(2,"peter@gmail.com");

        FriendManagement f = new FriendManagement();
        f.setPerson1(person1);
        f.setPerson2(person2);
        personRepository.save(person1);
        personRepository.save(person2);
        friendRepository.save(f);

        Assertions.assertThat(friendRepository.findAllFriendsEmail(person1.getEmail()).get(0)).isEqualTo(person2.getEmail());
        Assertions.assertThat(friendRepository.findAllFriendsEmail(person2.getEmail()).get(0)).isEqualTo(person1.getEmail());

    }
}
