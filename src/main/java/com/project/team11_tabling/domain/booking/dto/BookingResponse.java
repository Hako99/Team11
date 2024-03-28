package com.project.team11_tabling.domain.booking.dto;

import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookingResponse {

  private final Long bookingId;
  private final Long userId;
  private final Long shopId;
  private final Long ticketNumber;
  private final BookingType state;
  private final LocalDateTime reservedDateTime;
  private final Integer reservedParty;

  public BookingResponse(Booking booking) {
    this.bookingId = booking.getId();
    this.userId = booking.getUserId();
    this.shopId = booking.getShopId();
    this.ticketNumber = booking.getTicketNumber();
    this.state = booking.getState();
    this.reservedDateTime = booking.getReservedDatetime();
    this.reservedParty = booking.getReservedParty();
  }

}
