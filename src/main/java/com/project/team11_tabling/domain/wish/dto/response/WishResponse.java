package com.project.team11_tabling.domain.wish.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishResponse<T> {

  private int status;
  private String message;
  private T data;

  public static <T> WishResponse<T> success(int status) {
    return new WishResponse<>(status, null, null);
  }

  public static <T> WishResponse<T> success(int status, String message) {
    return new WishResponse<>(status, message, null);
  }

  public static <T> WishResponse<T> success(int status, T data) {
    return new WishResponse<>(status, null, data);
  }

  public static WishResponse fail(int status, String message) {
    return new WishResponse<>(status, message, null);
  }

  public static <T> WishResponse<T> fail(int status, T data) {
    return new WishResponse<>(status, null, data);
  }
}
