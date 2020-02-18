package com.example.command.demo.controller;

import com.blibli.oss.command.CommandExecutor;
import com.example.command.demo.CommandDemoApplication;
import com.example.command.demo.command.CreateUserCommand;
import com.example.command.demo.command.DeleteUserCommand;
import com.example.command.demo.command.GetUserCommand;
import com.example.command.demo.command.UpdateUserCommand;
import com.example.command.demo.enums.UserType;
import com.example.command.demo.model.command.CreateUserRequest;
import com.example.command.demo.model.command.UpdateUserRequest;
import com.example.command.demo.model.web.CreateUserResponse;
import com.example.command.demo.model.web.GetUserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @MockBean
  private CommandExecutor commandExecutor;

  @Autowired
  private MockMvc mockMvc;

  private CreateUserRequest createUserRequest;
  private CreateUserResponse createUserResponse;
  private GetUserResponse getUserResponse;
  private UpdateUserRequest updateUserRequest;
  private HttpHeaders httpHeaders;
  private final String request = "request";
  private final String response = "response";
  private final UserType userType = UserType.USER;

  @Before
  public void setUp() {
    initMocks(this);
    createUserRequest = CreateUserRequest.builder()
        .userName("userName")
        .userAddress("userAddress")
        .userGender("userGender")
        .build();
    createUserResponse = CreateUserResponse.builder()
        .userId("id")
        .userName("userName")
        .userAddress("userAddress")
        .userGender("userGender")
        .build();
    getUserResponse = GetUserResponse.builder()
        .userId("id")
        .userName("userName")
        .userAddress("userAddress")
        .userGender("userGender")
        .userType(userType)
        .build();
    updateUserRequest = UpdateUserRequest.builder()
        .userName("userName")
        .userAddress("userAddress")
        .userGender("userGender")
        .build();
    httpHeaders = new HttpHeaders();
  }

  @Test
  public void createUser() throws Exception {
    when(commandExecutor.execute(CreateUserCommand.class, createUserRequest)).thenReturn(
        Mono.just(createUserResponse));

    MockHttpServletRequestBuilder requestBuilder = post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(createUserRequest));

    mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
        .andExpect(status().isOk());

    verify(commandExecutor).execute(CreateUserCommand.class, createUserRequest);

  }

  @Test
  public void getUser() throws Exception {
    when(commandExecutor.execute(GetUserCommand.class, request)).thenReturn(
        Mono.just(getUserResponse));

    MockHttpServletRequestBuilder requestBuilder = get("/users?userName=" + request)
        .headers(httpHeaders)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
        .andExpect(status().isOk());

    verify(commandExecutor).execute(GetUserCommand.class, request);
  }

  @Test
  public void updateUser() throws Exception {
    when(commandExecutor.execute(UpdateUserCommand.class, updateUserRequest)).thenReturn(
        Mono.just(response));

    MockHttpServletRequestBuilder requestBuilder = put("/users")
        .headers(httpHeaders)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updateUserRequest));

    mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
        .andExpect(status().isOk());

    verify(commandExecutor).execute(UpdateUserCommand.class, updateUserRequest);
  }

  @Test
  public void deleteUser() throws Exception {
    when(commandExecutor.execute(DeleteUserCommand.class, request)).thenReturn(
        Mono.just(response));

    MockHttpServletRequestBuilder requestBuilder = delete("/users?userName=" + request)
        .headers(httpHeaders)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
        .andExpect(status().isOk());

    verify(commandExecutor).execute(DeleteUserCommand.class, request);
  }

}