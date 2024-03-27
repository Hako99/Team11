package com.project.team11_tabling.domain.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

  private String email;
  private String username;

  public UserResponseDto(String email, String username) {
    this.email = email;
    this.username = username;
  }
}


