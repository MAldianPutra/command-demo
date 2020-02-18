package com.example.command.demo.commandImpl;

import com.example.command.demo.command.DeleteUserCommand;
import com.example.command.demo.repository.UserReactiveRepository;
import com.example.command.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteUserCommandImpl implements DeleteUserCommand {

  private UserRepository userRepository;
  private UserReactiveRepository userReactiveRepository;

  public DeleteUserCommandImpl(UserRepository userRepository,
      UserReactiveRepository userReactiveRepository) {
    this.userRepository = userRepository;
    this.userReactiveRepository = userReactiveRepository;
  }

//  @Override
//  public Mono<String> execute(String userName) {
//    return Mono.fromCallable(() -> userRepository.getFirstByUserName(userName))
//        .map(user -> {
//          userRepository.delete(user);
//          return "User deleted successfully";
//        });
//  }

  @Override
  public Mono<String> execute(String userName) {
    return userReactiveRepository.getFirstByUserName(userName)
        .map(user -> {
          userReactiveRepository.delete(user);
          return "User deleted successfully";
        });
  }

}
