package com.example.friendmangerment.service;

import com.example.friendmangerment.dao.FriendRepository;
import com.example.friendmangerment.dao.PersonRepository;
import com.example.friendmangerment.exception.InValidEmailException;
import com.example.friendmangerment.model.Person;
import com.example.friendmangerment.model.response.ListRetrieveFriend;
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

import java.util.*;
import java.util.stream.Collector;

@RunWith(SpringRunner.class)
public class FriendManagementServiceTest {

    @TestConfiguration
    public static class FriendManagementServiceConfiguration{
        @Bean
        FriendManagementService friendManagementService(){
            return new FriendManagementServiceImp();
        }
    }

    @MockBean
    PersonRepository personRepository;

    @MockBean
    FriendRepository friendRepository;


    @Autowired
    private FriendManagementService friendManagementService;

    @Before
    public void setUp() {
        Person alex = new Person(1,"alex@gmail.com");
        Mockito.when(personRepository.findByEmail(alex.getEmail()))
                .thenReturn(alex);
        Person tom = new Person(2,"tom@gmail.com");
        Mockito.when(personRepository.findByEmail(tom.getEmail()))
                .thenReturn(tom);

        List<String> emailList = new ArrayList<>();
        emailList.add(alex.getEmail());
        emailList.add(tom.getEmail());
        Mockito.when(personRepository.existsByEmail("peter@gmail"))
                .thenReturn(true);
        Mockito.when(friendRepository.findAllFriendsEmail("peter@gmail"))
                .thenReturn(emailList);

        Mockito.when(personRepository.existsByEmail("a@gmail.com"))
                .thenReturn(true);
        Mockito.when(personRepository.existsByEmail("b@gmail.com"))
                .thenReturn(true);

        Mockito.when(friendRepository.findAllFriendsEmail("a@gmail.com"))
                .thenReturn(Arrays.asList("c@gmail.com","d@gmail.com"));

        Mockito.when(friendRepository.findAllFriendsEmail("b@gmail.com"))
                .thenReturn(Arrays.asList("d@gmail.com","f@gmail.com"));

    }

    @Test
    public void testCreateConnection(){
        Map<String, Boolean> res = friendManagementService.createConnection("tom@gmail.com","alex@gmail.com");
        Assert.assertEquals(res.get("status"),true);
    }

    @Test
    public void testRetrieveFriends(){
        List<String> emailList = Arrays.asList("alex@gmail.com","tom@gmail.com");
        ListRetrieveFriend res = friendManagementService.retrieveFriends("peter@gmail");
        Assert.assertEquals(res.isSuccess(),true);
        Assert.assertEquals(res.getFriends(),emailList);
        Assert.assertEquals(res.getCount(),emailList.size());

    }

    @Test
    public void testCommonFriends(){
        ListRetrieveFriend res = friendManagementService.commonFriends("a@gmail.com","b@gmail.com");
        System.out.println( res );
        Assert.assertEquals(res.isSuccess(),true);
        Assert.assertEquals(res.getFriends().get(0),"d@gmail.com");
        Assert.assertEquals(res.getCount(),1);
    }


}
