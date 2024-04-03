package com.project.team11_tabling.global.event;


import lombok.Getter;

@Getter
public class DoneEvent {

  private final Long shopId;
  private final Long userId;

  public DoneEvent(Long shopId, Long userId) {
    this.shopId = shopId;
    this.userId = userId;
  }

}
