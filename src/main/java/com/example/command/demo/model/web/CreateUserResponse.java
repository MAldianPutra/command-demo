package com.example.command.demo.model.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {

    private String userId;

    private String userName;

    private String userAddress;

    private String userGender;

}
