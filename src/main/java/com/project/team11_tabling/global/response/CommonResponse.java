package com.project.team11_tabling.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

  private T data;

  public static <T> ResponseEntity<CommonResponse<T>> ok(T data) {
    return ResponseEntity.ok().body(CommonResponse.<T>builder().data(data).build());
  }
}
