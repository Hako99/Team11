package com.project.team11_tabling.global.batch;

import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookingCsvDto extends Booking{

  private Long userId;
  private Long shopId;
  private Long ticketNumber;
  private BookingType state;
  private LocalDateTime reservedDatetime;
  private Integer reservedParty;
}
