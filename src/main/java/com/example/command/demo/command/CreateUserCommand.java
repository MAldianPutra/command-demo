package com.example.command.demo.command;

import com.blibli.oss.command.Command;
import com.example.command.demo.model.command.CreateUserRequest;
import com.example.command.demo.model.web.CreateUserResponse;

public interface CreateUserCommand extends Command<CreateUserRequest, CreateUserResponse> {

}
