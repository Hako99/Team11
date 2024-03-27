package com.project.team11_tabling.domain.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommonResponse<T> {
  private String msg;
  private T data;
  public CommonResponse(String msg) {
    this.msg = msg;
  }
}
