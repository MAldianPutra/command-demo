package com.example.command.demo.commandImpl;

import com.example.command.demo.entity.User;
import com.example.command.demo.enums.UserType;
import com.example.command.demo.model.web.GetUserResponse;
import com.example.command.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetUserCommandImplTest {

  @InjectMocks
  private GetUserCommandImpl getUserCommand;

  @Mock
  private UserRepository userRepository;

  private User user;
  private GetUserResponse response;
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
    response = GetUserResponse.builder()
        .userId("id")
        .userName("userName")
        .userAddress("userAddress")
        .userGender("userGender")
        .userType(userType)
        .build();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void execute() {
    when(userRepository.getFirstByUserName(userName)).thenReturn(
        Mono.just(user));

    GetUserResponse result = getUserCommand.execute(userName).block();
    assertEquals(response, result);

    verify(userRepository).getFirstByUserName(userName);
  }

}