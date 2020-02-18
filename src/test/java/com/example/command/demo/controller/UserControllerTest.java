package com.example.command.demo.controller;

import com.blibli.oss.command.CommandExecutor;
import com.example.command.demo.CommandDemoApplication;
import com.example.command.demo.command.CreateUserCommand;
import com.example.command.demo.command.GetUserCommand;
import com.example.command.demo.model.command.CreateUserRequest;
import com.example.command.demo.model.command.UpdateUserRequest;
import com.example.command.demo.model.web.CreateUserResponse;
import com.example.command.demo.model.web.GetUserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommandDemoApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

  @MockBean
  private CommandExecutor commandExecutor;

  @Autowired
  private MockMvc mockMvc;

  private CreateUserRequest createUserRequest;
  private CreateUserResponse createUserResponse;
  private GetUserResponse getUserResponse;
  private UpdateUserRequest updateUserRequest;
  private HttpHeaders httpHeaders;
  private String request;

  @Before
  public void setUp() {
    initMocks(this);
    createUserRequest = CreateUserRequest.builder()
        .userName("Test")
        .userAddress("Test")
        .userGender("Test")
        .build();
    createUserResponse = CreateUserResponse.builder()
        .userId("Test")
        .userName("Test")
        .userAddress("Test")
        .userGender("Test")
        .build();
    httpHeaders = new HttpHeaders();
  }

  @Test
  void createUser() throws Exception {
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
  void getUser() throws Exception {
    when(commandExecutor.execute(GetUserCommand.class, request)).thenReturn(
        Mono.just(getUserResponse));

    MockHttpServletRequestBuilder requestBuilder = get("/users")
        .headers(httpHeaders)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
        .andExpect(status().isOk());

    verify(commandExecutor).execute(GetUserCommand.class, request);
  }

  @Test
  void updateUser() {
  }

  @Test
  void deleteUser() {
  }
}