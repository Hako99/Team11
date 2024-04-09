package com.project.team11_tabling.global.event;

import lombok.Getter;

@Getter
public class CancelEvent {

  private Long shopId;
  private Long userId;

  public CancelEvent(Long shopId, Long userId) {
    this.shopId = shopId;
    this.userId = userId;
  }

}
