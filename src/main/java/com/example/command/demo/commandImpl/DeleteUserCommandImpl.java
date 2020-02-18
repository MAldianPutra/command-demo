package com.example.command.demo.commandImpl;

import com.example.command.demo.command.DeleteUserCommand;
import com.example.command.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteUserCommandImpl implements DeleteUserCommand {

  private UserRepository userRepository;

  @Autowired
  public DeleteUserCommandImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Mono<String> execute(String userName) {
    return userRepository.getFirstByUserName(userName)
        .flatMap(user -> userRepository.delete(user)
            .thenReturn("User deleted successfully."));
  }



}
