package com.example.command.demo.model.web;

import com.example.command.demo.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {

  private String userId;

  private String userName;

  private String userAddress;

  private String userGender;

  private UserType userType;

}
