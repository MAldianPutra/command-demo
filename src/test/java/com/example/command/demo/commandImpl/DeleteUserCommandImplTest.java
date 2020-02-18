package com.example.command.demo.commandImpl;

import com.example.command.demo.entity.User;
import com.example.command.demo.enums.UserType;
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

class DeleteUserCommandImplTest {

  @InjectMocks
  DeleteUserCommandImpl deleteUserCommand;

  @Mock
  UserReactiveRepository userReactiveRepository;

  private User user;
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
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(userReactiveRepository);
  }

  @Test
  void execute() {
    when(userReactiveRepository.getFirstByUserName(userName)).thenReturn(
        Mono.just(user));

    String response = deleteUserCommand.execute(userName).block();
    assertEquals("User deleted successfully", response);

    verify(userReactiveRepository).getFirstByUserName(userName);
    verify(userReactiveRepository).delete(user);
  }
}