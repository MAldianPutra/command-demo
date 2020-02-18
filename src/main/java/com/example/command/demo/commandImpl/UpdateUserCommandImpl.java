package com.example.command.demo.commandImpl;

import com.example.command.demo.command.UpdateUserCommand;
import com.example.command.demo.entity.User;
import com.example.command.demo.model.command.UpdateUserRequest;
import com.example.command.demo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateUserCommandImpl implements UpdateUserCommand {

  private UserRepository userRepository;

  @Autowired
  public UpdateUserCommandImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Mono<String> execute(UpdateUserRequest request) {
    return userRepository.getFirstByUserName(request.getUserName())
        .map(user -> updateUser(request, user))
        .flatMap(user -> userRepository.save(user)
            .thenReturn("User updated successfully."));
  }

  private User updateUser(UpdateUserRequest request, User user) {
    BeanUtils.copyProperties(request, user);
    return user;
  }

}
