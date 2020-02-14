package com.example.command.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = User.COLLECTION_NAME)
public class User {

    public static final String COLLECTION_NAME = "user";
    public static final String FIELD_ID = "userId";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_GENDER = "gender";

    @Id
    @Field(value = User.FIELD_ID)
    private String userId;

    @Indexed(unique = true)
    @Field(value = User.FIELD_NAME)
    private String userName;

    @Field(value = User.FIELD_ADDRESS)
    private String userAddress;

    @Field(value = User.FIELD_GENDER)
    private String userGender;

}