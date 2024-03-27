package com.project.team11_tabling.domain.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {

  private String email;
  private String password;
  private Long phoneNumber;
}
