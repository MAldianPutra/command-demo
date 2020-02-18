package com.example.command.demo.repository;

import com.example.command.demo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  User getFirstByUserName(String userName);

  Long deleteByUserName(String userName);

}
