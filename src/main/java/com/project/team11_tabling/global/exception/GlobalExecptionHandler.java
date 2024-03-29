package com.project.team11_tabling.global.exception;

import com.project.team11_tabling.global.exception.custom.NotFoundException;
import com.project.team11_tabling.global.exception.custom.UserNotMatchException;
import com.project.team11_tabling.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler -> ")
public class GlobalExecptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public void methodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error("MethodArgumentNotValidException: ", e);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException e) {
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ErrorResponse> duplicateException(IllegalStateException e) {
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<ErrorResponse> duplicateException(NullPointerException e) {
    ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler({NotFoundException.class, UserNotMatchException.class})
  public ResponseEntity<ErrorResponse> notFoundException(NotFoundException e) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(e.getMessage()));
  }

}
