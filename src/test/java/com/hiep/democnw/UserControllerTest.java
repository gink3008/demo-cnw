package com.hiep.democnw;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiep.democnw.Controller.UserController;
import com.hiep.democnw.Dao.RequestObject.UserRequest;
import com.hiep.democnw.Dao.Services.UserService;
import com.hiep.democnw.Entities.real.UsersEntity;
import com.hiep.democnw.Exception.UserUnknowException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserRequest> UserRequestArgumentCaptor;

    @Test
    public void postingANewUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Lehoanghiep");
        userRequest.setPassword("123456");

        when(userService.createNewUser(UserRequestArgumentCaptor.capture())).thenReturn(true);

        this.mockMvc
                .perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/books/1"));


        assertThat(UserRequestArgumentCaptor.getValue().getUsername(), is("Lehaonghiep"));
        assertThat(UserRequestArgumentCaptor.getValue().getPassword(), is("123456"));

    }

    @Test
    public void getallbookEndpointShouldTwoUsers() throws Exception {
        List<UsersEntity> list = new ArrayList<UsersEntity>();
        list.add(createUsers(3, "LeHoangHiep", "123456"));
        list.add(createUsers(4, "LeHoang", "123456"));
        when(userService.getAllUsers()).thenReturn(list);
        this.mockMvc.
                perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("LeHoangHiep")))
                .andExpect(jsonPath("$[0].password", is("123456")));
    }

    @Test
    public void getUserWithOneReturnUser() throws Exception {

        when(userService.getUserById(1)).thenReturn(createUsers(4, "LeHoangHiep", "123456"));
        this.mockMvc.
                perform(get("/api/users/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.username", is("LeHoangHiep")))
                .andExpect(jsonPath("$.password", is("123456")));
    }

    @Test
    public void getUserWithUnknowId() throws Exception {
        when(userService.getUserById(1)).thenThrow(new UserUnknowException("User with id '42' is not found"));
        this.mockMvc.
                perform(get("/api/users/4"))
                .andExpect(status().isNotFound());

    }

    private UsersEntity createUsers(int id, String username, String password) {
        UsersEntity users = new UsersEntity();
        users.setUsername(username);
        users.setPassword(password);
        users.setIdUser(id);
        return users;
    }
}
