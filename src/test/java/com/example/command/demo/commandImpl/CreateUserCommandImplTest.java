package com.example.command.demo.commandImpl;

import com.example.command.demo.entity.User;
import com.example.command.demo.enums.UserType;
import com.example.command.demo.model.command.CreateUserRequest;
import com.example.command.demo.model.web.CreateUserResponse;
import com.example.command.demo.repository.UserReactiveRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CreateUserCommandImplTest {

  @InjectMocks
  CreateUserCommandImpl createUserCommand;

  @Mock
  UserReactiveRepository userReactiveRepository;

  private User user;
  private CreateUserRequest request;
  private CreateUserResponse response;
  private final UserType userType = UserType.USER;

  @BeforeEach
  void setUp() {
    initMocks(this);
    user = User.builder()
        .userId("id")
        .userName("userName")
        .userAddress("userAddress")
        .userGender("userGender")
        .userType(userType)
        .build();
    request = CreateUserRequest.builder()
        .userName("userName")
        .userAddress("userAddress")
        .userGender("userGender")
        .build();
    response = CreateUserResponse.builder()
        .userId("id")
        .userName("userName")
        .userAddress("userAddress")
        .userGender("userGender")
        .build();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(userReactiveRepository);
  }

  @Test
  void execute() {
    when(userReactiveRepository.save(any(User.class))).thenReturn(
        Mono.just(user));

    CreateUserResponse result = createUserCommand.execute(request).block();
    assertEquals(this.response.getUserName(), result.getUserName());
    assertEquals(this.response.getUserAddress(), result.getUserAddress());
    assertEquals(this.response.getUserGender(), result.getUserGender());

    verify(userReactiveRepository).save(any(User.class));
  }
}