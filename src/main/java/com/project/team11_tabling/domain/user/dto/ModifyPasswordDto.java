package com.project.team11_tabling.domain.user.dto;

import lombok.Getter;

@Getter
public class ModifyPasswordDto {

  private String password;
  private String changePassword;
  private String changePasswordConfirm;
}
