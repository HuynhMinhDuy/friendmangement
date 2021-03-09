package com.example.friendmangerment.controller;

import com.example.friendmangerment.exception.ResourceNotFoundException;
import com.example.friendmangerment.model.FriendManagement;
import com.example.friendmangerment.model.response.ListRetrieveFriend;
import com.example.friendmangerment.service.FriendManagementService;
import com.example.friendmangerment.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(FriendController.class)
public class FriendControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FriendManagementService friendManagementService;

    private String toJson(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }

    @Test
    public void createFriendConnection() throws Exception {
        String email1 = "tom@gmail.com", email2 = "john@gmail.com";
        Map<String, Boolean> res = new HashMap<>();
        res.put("status",true);
        List<String> friends = Arrays.asList(email1, email2);
        String json = toJson(ImmutableMap.of("friends", friends));
        when(friendManagementService.createConnection(email1,email2)).thenReturn(res);
        mockMvc.perform(post("/api/v1/connect").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    public void createFriendConnectionResourceNotFound() throws Exception {
        String email1 = "NotFoundEmail@gmail.com", email2 = "john@gmail.com";
        List<String> friends = Arrays.asList(email1, email2);
        String json = toJson(ImmutableMap.of("friends", friends));
        when(friendManagementService.createConnection(email1,email2)).thenThrow(new ResourceNotFoundException());
        mockMvc.perform(post("/api/v1/connect").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("404"));
    }

    @Test
    public void retrieveFriendList() throws Exception {
        String json = "{\"email\":\"tom@gmail.com\"}";
        List<String> friendList = Arrays.asList("john@gmail.com");
        ListRetrieveFriend listRetrieveFriend = ListRetrieveFriend.builder()
                                                .success(true)
                                                .friends(friendList)
                                                .count(1)
                                                .build();

        when(friendManagementService.retrieveFriends("tom@gmail.com")).thenReturn(listRetrieveFriend);

        mockMvc.perform(post("/api/v1/list").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.count", is(1)))
                .andExpect(jsonPath("$.friends[0]", is("john@gmail.com")));
    }

    @Test
    public void retrieveCommonFriends() throws Exception {

        String email1 = "tom@gmail.com";
        String email2 = "john@gmail.com";
        String commonEmail = "common@gmail.com";

        String json = toJson(ImmutableMap.of("friends", Arrays.asList(email1, email2)));

        ListRetrieveFriend listRetrieveFriend = ListRetrieveFriend.builder()
                .success(true)
                .friends(Arrays.asList(commonEmail))
                .count(1)
                .build();

        when(friendManagementService.commonFriends(email1,email2)).thenReturn(listRetrieveFriend);

        mockMvc.perform(post("/api/v1/common").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.friends[0]", is("common@gmail.com")));
    }

}
