package com.example.command.demo.commandImpl;

import com.example.command.demo.command.DeleteUserCommand;
import com.example.command.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteUserCommandImpl implements DeleteUserCommand {

    @Autowired
    UserRepository userRepository;

    @Override
    public Mono<String> execute(String userName) {
        return Mono.fromCallable(() -> userRepository.getFirstByUserName(userName))
                .map(user -> {
                    userRepository.delete(user);
                    return "User deleted successfully";
                });
    }

}