package com.project.team11_tabling.user.controller;

import com.project.team11_tabling.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

}
