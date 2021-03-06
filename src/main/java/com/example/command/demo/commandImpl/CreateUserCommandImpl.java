package com.example.command.demo.commandImpl;

import com.example.command.demo.command.CreateUserCommand;
import com.example.command.demo.entity.User;
import com.example.command.demo.enums.UserType;
import com.example.command.demo.model.command.CreateUserRequest;
import com.example.command.demo.model.web.CreateUserResponse;
import com.example.command.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class CreateUserCommandImpl implements CreateUserCommand {

  private UserRepository userRepository;

  @Autowired
  public CreateUserCommandImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Mono<CreateUserResponse> execute(CreateUserRequest request) {
    return Mono.fromCallable(() -> createUser(request))
        .flatMap(user -> userRepository.save(user))
        .map(this::createUserResponse);
  }

  private User createUser(CreateUserRequest request) {
    User user = User.builder()
        .userId(UUID.randomUUID().toString())
        .userType(UserType.USER)
        .build();
    BeanUtils.copyProperties(request, user);
    return user;
  }

  private CreateUserResponse createUserResponse(User user) {
    CreateUserResponse response = new CreateUserResponse();
    BeanUtils.copyProperties(user, response);
    return response;
  }

}
