package com.example.command.demo.commandImpl;

import com.example.command.demo.command.GetUserCommand;
import com.example.command.demo.entity.User;
import com.example.command.demo.model.web.GetUserResponse;
import com.example.command.demo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetUserCommandImpl implements GetUserCommand {

    @Autowired
    UserRepository userRepository;

    @Override
    public Mono<GetUserResponse> execute(String userName) {
        return Mono.fromCallable(() -> userRepository.getFirstByUserName(userName))
                .map(this::getGetUserResponse);
    }

    private GetUserResponse getGetUserResponse(User user) {
        GetUserResponse response = new GetUserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }
}
