package com.example.friendmangerment.service;

import com.example.friendmangerment.exception.ResourceAlreadyExist;
import com.example.friendmangerment.exception.ResourceNotFoundException;
import com.example.friendmangerment.model.FriendManagement;
import com.example.friendmangerment.model.response.ListRetrieveFriend;

import java.util.List;
import java.util.Map;

public interface FriendManagementService {
    Map<String, Boolean> createConnection(String emailPerson1, String emailPerson2) throws ResourceNotFoundException, ResourceAlreadyExist;
    ListRetrieveFriend retrieveFriends(String email) throws ResourceNotFoundException;
    ListRetrieveFriend commonFriends(String emailPerson1, String emailPerson2) throws ResourceNotFoundException;

}
