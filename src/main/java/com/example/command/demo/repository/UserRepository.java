package com.example.command.demo.repository;

import com.example.command.demo.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

  Mono<User> getFirstByUserName(String userName);

}
