package com.project.team11_tabling.domain.booking.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BookingRequest {

  private Long shopId;
  private LocalDateTime reservedDatetime;
  private Integer bookingParty;

}
