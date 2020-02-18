package com.example.command.demo.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.example.command.demo.command.CreateUserCommand;
import com.example.command.demo.command.DeleteUserCommand;
import com.example.command.demo.command.GetUserCommand;
import com.example.command.demo.command.UpdateUserCommand;
import com.example.command.demo.model.command.CreateUserRequest;
import com.example.command.demo.model.command.UpdateUserRequest;
import com.example.command.demo.model.web.CreateUserResponse;
import com.example.command.demo.model.web.GetUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
public class UserController {

  private CommandExecutor commandExecutor;

  @Autowired
  public UserController(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<CreateUserResponse>> createUser(@RequestBody CreateUserRequest request) {
    return commandExecutor.execute(CreateUserCommand.class, request)
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<GetUserResponse>> getUser(@RequestParam String userName) {
    return commandExecutor.execute(GetUserCommand.class, userName)
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @PutMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<String>> updateUser(@RequestBody UpdateUserRequest request) {
    return commandExecutor.execute(UpdateUserCommand.class, request)
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @DeleteMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<String>> deleteUser(@RequestParam String userName) {
    return commandExecutor.execute(DeleteUserCommand.class, userName)
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

}
