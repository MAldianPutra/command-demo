package com.example.command.demo.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank
    @Length(max = 40)
    private String userName;

    @NotBlank
    @Length(max = 40)
    private String userAddress;

    @NotBlank
    @Length(max = 10)
    private String userGender;

}
