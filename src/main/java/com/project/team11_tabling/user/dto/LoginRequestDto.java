package com.project.team11_tabling.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {

  private String email;
  private String password;
  private Long phoneNumber;
}
