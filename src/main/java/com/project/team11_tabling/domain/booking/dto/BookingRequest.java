package com.project.team11_tabling.domain.booking.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BookingRequest {

  @NotBlank
  private Long shopId;

  @NotBlank
  private LocalDateTime reservedDatetime;

  @NotBlank
  private Integer bookingParty;

}
