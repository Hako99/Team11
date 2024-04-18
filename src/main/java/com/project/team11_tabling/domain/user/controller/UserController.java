package com.project.team11_tabling.domain.user.controller;

import com.project.team11_tabling.domain.user.dto.LoginRequestDto;
import com.project.team11_tabling.domain.user.dto.LoginResponseDto;
import com.project.team11_tabling.domain.user.dto.ResponseDto;
import com.project.team11_tabling.domain.user.dto.SignupRequestDto;
import com.project.team11_tabling.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/test")
  public ResponseEntity<ResponseDto> test(){
    return ResponseEntity.ok().body(ResponseDto.success(200));
  }

  @PostMapping("/signup")
  public ResponseEntity<ResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto,
      BindingResult bindingResult) {

    System.out.println(requestDto);

    String errorMessages = "";
    if (bindingResult.hasErrors()) {
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        errorMessages +=
            fieldError.getField() + " : " + fieldError.getDefaultMessage() + "\n";
      }
      return ResponseEntity.badRequest().body(ResponseDto.fail(400, errorMessages));
    }

    userService.signup(requestDto);

    return ResponseEntity.ok().body(ResponseDto.success(200));
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto request,
      HttpServletResponse response) {

    LoginResponseDto responseDto = userService.login(request, response);

    return ResponseEntity.ok().body(ResponseDto.success(200, responseDto));
  }
}

