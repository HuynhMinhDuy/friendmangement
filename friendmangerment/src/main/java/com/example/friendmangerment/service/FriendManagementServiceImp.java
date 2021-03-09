package com.example.friendmangerment.service;

import com.example.friendmangerment.dao.FriendRepository;
import com.example.friendmangerment.dao.PersonRepository;
import com.example.friendmangerment.exception.ResourceAlreadyExist;
import com.example.friendmangerment.exception.ResourceNotFoundException;
import com.example.friendmangerment.model.FriendManagement;
import com.example.friendmangerment.model.Person;
import com.example.friendmangerment.model.response.ListRetrieveFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FriendManagementServiceImp implements FriendManagementService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FriendRepository friendRepository;

    @Override
    public Map<String, Boolean> createConnection(String emailPerson1, String emailPerson2) throws ResourceNotFoundException, ResourceAlreadyExist {

        Person person1 =  personRepository.findByEmail(emailPerson1);
        Person person2 =  personRepository.findByEmail(emailPerson2);

        if ( person1 == null||person2 == null) {
            throw new ResourceNotFoundException();
        }

        FriendManagement friendManagement = new FriendManagement();

        if(person1.getId()<person2.getId()){
            friendManagement.setPerson1(person1);
            friendManagement.setPerson2(person2);
        } else {
            friendManagement.setPerson1(person2);
            friendManagement.setPerson2(person1);
        }


        if (friendRepository.exists(Example.of(friendManagement))) {
           throw new ResourceAlreadyExist();
        }

        friendRepository.save(friendManagement);
        Map<String, Boolean> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    @Override
    public ListRetrieveFriend retrieveFriends(String email) throws ResourceNotFoundException {

        if(personRepository.existsByEmail(email) == false) {
            throw new ResourceNotFoundException();
        }

        List<String> friends = friendRepository.findAllFriendsEmail(email);

        ListRetrieveFriend listRetrieveFriend =ListRetrieveFriend.builder()
                                                                 .friends(friends)
                                                                 .count(friends.size())
                                                                 .success(true)
                                                                 .build();
        return listRetrieveFriend;
    }

    @Override
    public ListRetrieveFriend commonFriends(String emailPerson1, String emailPerson2) throws ResourceNotFoundException {

        if(personRepository.existsByEmail(emailPerson1)== false ||personRepository.existsByEmail(emailPerson2)== false ) {
            throw new ResourceNotFoundException();
        }

        List<String> friendsListEmail1 = friendRepository.findAllFriendsEmail(emailPerson1);
        List<String> friendsListEmail2 = friendRepository.findAllFriendsEmail(emailPerson2);

        List<String> commonFriends = friendsListEmail1.stream()
                                    .filter(p -> friendsListEmail2.contains(p))
                                    .collect(Collectors.toList());

        return ListRetrieveFriend.builder()
                .friends(commonFriends)
                .count(commonFriends.size())
                .success(true)
                .build();
    }


}
