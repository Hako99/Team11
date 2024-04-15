package com.project.team11_tabling.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class LoginResponseDto {
  private String token;
}
