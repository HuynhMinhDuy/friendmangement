package com.example.friendmangerment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@SpringBootTest(classes = FriendmangermentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
class FriendmangermentApplicationTests extends AbstractTestNGSpringContextTests {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@BeforeMethod
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	private String toJson(Object o) throws Exception {
		return objectMapper.writeValueAsString(o);
	}


	public void registerEmail(String email) throws Exception {
		String json = toJson(ImmutableMap.of("email", email));
		mockMvc.perform(post("/api/v1/register").content(json).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value(true));
	}

	@Test(groups = "/api/v1/register")
	public void registerInvalidEmailException() throws Exception {
		String json = toJson(ImmutableMap.of("email", "test@gmailcom"));
		mockMvc.perform(post("/api/v1/register").content(json).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("400"));
	}

	@Test(groups = "/api/v1/register")
	public void registerEmailRepeatedApiException() throws Exception {
		String json = toJson(ImmutableMap.of("email", "test@gmail.com"));
		mockMvc.perform(post("/api/v1/register").content(json).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value(true));
		mockMvc.perform(post("/api/v1/register").content(json).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isConflict())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("409"))
				.andExpect(jsonPath("$.error").value("Conflict"));
	}

	@Test(groups = "/api/v1/connect")
	public void createFriendConnection() throws Exception {
		String email1 = "tom@gmail.com", email2 = "john@gmail.com";
		registerEmail(email1);
		registerEmail(email2);
		createFriendConnection(email1, email2);
	}

	public void createFriendConnection(String email1, String email2) throws Exception {
		List<String> friends = Arrays.asList(email1, email2);
		String json = toJson(ImmutableMap.of("friends", friends));
		mockMvc.perform(post("/api/v1/connect").content(json).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value(true));
	}

	@Test(groups = "/api/v1/connect")
	public void createFriendConnectionResourceNotFound() throws Exception {
		String email1 = "perter@gmail.com", email2 = "john@gmail.com";
		List<String> friends = Arrays.asList(email1, email2);
		String json = toJson(ImmutableMap.of("friends", friends));
		mockMvc.perform(post("/api/v1/connect").content(json).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("404"));
	}

	@Test(groups = "/api/v1/list", dependsOnGroups = "/api/v1/connect")
	public void retrieveFriendList() throws Exception {
		String json = "{\"email\":\"tom@gmail.com\"}";
		mockMvc.perform(post("/api/v1/list").content(json).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.count", is(1)))
				.andExpect(jsonPath("$.friends[0]", is("john@gmail.com")));
	}

	@Test(groups = "/api/v1/common", dependsOnGroups = {"/api/v1/list"})
	public void retrieveCommonFriends() throws Exception {
		registerEmail("common@gmail.com");
		createFriendConnection("tom@gmail.com", "common@gmail.com");
		createFriendConnection("john@gmail.com", "common@gmail.com");
		String json = toJson(ImmutableMap.of("friends", Arrays.asList("tom@gmail.com", "john@gmail.com")));
		mockMvc.perform(post("/api/v1/common").content(json).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.count").value(1))
				.andExpect(jsonPath("$.friends[0]", is("common@gmail.com")));
	}


}

