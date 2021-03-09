package com.example.friendmangerment.controller;


import com.example.friendmangerment.exception.ResourceAlreadyExist;
import com.example.friendmangerment.exception.ResourceNotFoundException;
import com.example.friendmangerment.model.FriendManagement;
import com.example.friendmangerment.model.request.DoubleEmailRequest;
import com.example.friendmangerment.model.request.SingleEmailRequest;
import com.example.friendmangerment.model.response.ListRetrieveFriend;
import com.example.friendmangerment.service.FriendManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class FriendController {

    @Autowired
    FriendManagementService friendManagementService;

    @PostMapping("/connect")
    public Map<String, Boolean> createConnect(@RequestBody DoubleEmailRequest connectList) throws ResourceNotFoundException, ResourceAlreadyExist {
        return friendManagementService.createConnection(connectList.getFriends().get(0),connectList.getFriends().get(1) );

    }

    @PostMapping("/list")
    public ListRetrieveFriend retrieveFriendList(@RequestBody SingleEmailRequest req) throws ResourceNotFoundException {
        return friendManagementService.retrieveFriends(req.getEmail());
    }

    @PostMapping("/common")
    public ListRetrieveFriend commonFriend(@RequestBody DoubleEmailRequest friendList) throws ResourceNotFoundException {
        return friendManagementService.commonFriends(friendList.getFriends().get(0),friendList.getFriends().get(1) );
    }


}
