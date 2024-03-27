package com.project.team11_tabling.domain.user.controller;

import com.project.team11_tabling.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

}
