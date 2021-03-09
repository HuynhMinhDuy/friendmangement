package com.example.friendmangerment.controller;


import com.example.friendmangerment.FriendmangermentApplication;
import com.example.friendmangerment.exception.ApiError;
import com.example.friendmangerment.exception.InValidEmailException;
import com.example.friendmangerment.exception.ResourceAlreadyExist;
import com.example.friendmangerment.model.Person;
import com.example.friendmangerment.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;



import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonService personService;


    private String toJson(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }


    @Test
    public void registerSuccessEmail() throws Exception {
        Person p =  new Person(0,"abc@gmail.com");
        Map<String, Boolean> res = new HashMap<>();
        res.put("status",true);
        when(personService.register(p)).thenReturn(res);
        String json = toJson(ImmutableMap.of("email", p.getEmail()));
        mockMvc.perform(post("/api/v1/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    public void registerEmailRepeatedApiException() throws Exception {
        Person p =  new Person(0,"conflict@gmail.com");
        when(personService.register(p)).thenThrow(new ResourceAlreadyExist());
        String json = toJson(ImmutableMap.of("email", p.getEmail()));
        mockMvc.perform(post("/api/v1/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("409"))
                .andExpect(jsonPath("$.error").value("Conflict"));
    }

    @Test
    public void registerInvalidEmailException() throws Exception {
        Person p =  new Person(0,"invalid@gmail.com");
        when(personService.register(p)).thenThrow(new InValidEmailException());
        String json = toJson(ImmutableMap.of("email", p.getEmail()));
        mockMvc.perform(post("/api/v1/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("400"));
    }
}
