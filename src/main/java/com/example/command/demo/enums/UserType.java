package com.example.command.demo.enums;

public enum UserType {

  USER("USER"),
  MERCHANT("MERCHANT"),
  ADMIN("ADMIN");

  private String userType;

  UserType(String userType) {
    this.userType = userType;
  }

  public String getUserType() {
    return userType;
  }

}
