package com.example.command.demo.commandImpl;

import com.example.command.demo.entity.User;
import com.example.command.demo.enums.UserType;
import com.example.command.demo.model.command.UpdateUserRequest;
import com.example.command.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class UpdateUserCommandImplTest {

  @InjectMocks
  UpdateUserCommandImpl updateUserCommand;

  @Mock
  UserRepository userRepository;

  private User user;
  private UpdateUserRequest request;
  private final String userName = "userName";
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
    request = UpdateUserRequest.builder()
        .userName("userName")
        .userAddress("userAddress")
        .userGender("userGender")
        .build();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void execute() {
    when(userRepository.save(any(User.class))).thenReturn(
        Mono.just(user));
    when(userRepository.getFirstByUserName(userName)).thenReturn(
        Mono.just(user));

    String result = updateUserCommand.execute(request).block();
    assertEquals("User updated successfully.", result);

    verify(userRepository).save(any(User.class));
    verify(userRepository).getFirstByUserName(userName);
  }
}