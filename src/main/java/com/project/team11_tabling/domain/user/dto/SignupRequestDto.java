package com.project.team11_tabling.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

  @Pattern(regexp = "^[a-z0-9]{4,10}", message = "username은 숫자 및 알파벳 소문자 4~10자로 입력해주세요.")
  private String username;

  @Email(message = "옳바른 email 형식이 아닙니다.")
  private String email;

  @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?]{8,15}", message = "비밀번호는 숫자 및 알파벳 대소문자 그리고 특수문자를 포함한 8~15자로 입력해주세요.")
  private String password;

  @NotBlank
  private String phoneNumber;
}
