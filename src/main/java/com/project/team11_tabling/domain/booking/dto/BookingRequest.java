package com.project.team11_tabling.domain.booking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BookingRequest {

  @Positive
  @NotNull
  private Long shopId;

  @NotNull
  private LocalDateTime reservedDatetime;

  @Positive
  @NotNull
  private Integer reservedParty;

}
