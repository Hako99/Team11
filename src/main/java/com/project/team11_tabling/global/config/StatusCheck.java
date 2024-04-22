package com.project.team11_tabling.global.config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusCheck {

  @GetMapping("/health-check")
  public ResponseEntity<Void> checkHealthStatus() {

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
