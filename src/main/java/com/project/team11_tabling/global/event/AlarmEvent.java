package com.project.team11_tabling.global.event;

import com.project.team11_tabling.domain.booking.entity.Booking;
import lombok.Getter;

@Getter
public class AlarmEvent {

  private Booking booking;

  public AlarmEvent (Booking booking) {
    this.booking = booking;
  }

}
