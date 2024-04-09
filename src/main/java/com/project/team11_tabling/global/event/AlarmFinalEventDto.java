package com.project.team11_tabling.global.event;

import lombok.Getter;

@Getter
public class AlarmFinalEventDto {

  private final String message;
  private final Long userId;


  public AlarmFinalEventDto(String message,Long userId){
    this.message = message;
    this.userId = userId;
  }
}
