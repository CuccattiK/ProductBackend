package com.cuccatti.inventory.test.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cuccatti.inventory.controller.UserController;
import com.cuccatti.inventory.filter.CORSFilter;
import com.cuccatti.inventory.model.User;
import com.cuccatti.inventory.service.UserService;
import com.cuccatti.inventory.util.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTest {

	final static String URL = "http://localhost:8080";
	final static String ENDPOINT = "/api/users/";
	final static String GET_ALL_USERS = URL + ENDPOINT;
	final static String GET_USER_BY_ID = URL + ENDPOINT + "{id}";

	final static String CREATE_USER = URL + ENDPOINT;
	final static String UPDATE_USER = URL + ENDPOINT;
	final static String DELETE_USER = URL + ENDPOINT + "{id}";

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).addFilters(new CORSFilter()).build();
	}

	@Test
	public void get_all_user_success() throws Exception {
		List<User> users = Arrays.asList(new User(1, "John", "Doe", "jd@yahoo.com"), new User(2, "Mary", "Smith", "ms@yahoo.com"));
		when(userService.getAllUsers()).thenReturn(users);
		mockMvc.perform(get(GET_ALL_USERS)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].firstName", is("John"))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].firstName", is("Mary")));
		verify(userService, times(1)).getAllUsers();
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void get_user_by_id_success() throws Exception {
		User user = new User(1, "John", "Doe", "jd@yahoo.com");
		when(userService.findById(1L)).thenReturn(user);
		mockMvc.perform(get(GET_USER_BY_ID, 1)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.firstName", is("John")));
		verify(userService, times(1)).findById(1L);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void create_user_success() throws Exception {
		User user = new User(1, "John", "Doe", "jd@yahoo.com");
		when(userService.createUser(user)).thenReturn(user);
		mockMvc.perform(
				post(CREATE_USER).contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(user)))
				.andExpect(status().isOk());
		verify(userService, times(1)).createUser(refEq(user));
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void update_user_success() throws Exception {
		User user = new User(1, "John", "Doe", "jd@yahoo.com");

		when(userService.findById(user.getId())).thenReturn(user);
		when(userService.updateUser(user)).thenReturn(user);

		mockMvc.perform(
				put(UPDATE_USER).contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(user)))
				.andExpect(status().isOk());

		verify(userService, times(1)).updateUser(refEq(user));
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void delete_user_success() throws Exception {
		User user = new User(1, "John", "Doe", "jd@yahoo.com");

		doNothing().when(userService).deleteUser(user.getId());
		mockMvc.perform(delete(DELETE_USER, user.getId())).andExpect(status().isOk());

		verify(userService, times(1)).deleteUser(user.getId());
		verifyNoMoreInteractions(userService);
	}

}
