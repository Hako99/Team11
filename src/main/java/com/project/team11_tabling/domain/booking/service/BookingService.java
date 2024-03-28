package com.project.team11_tabling.domain.booking.service;

import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import java.util.List;

public interface BookingService {

  BookingResponse booking(BookingRequest request);

  void deleteBooking(Long bookingId);

  List<BookingResponse> getMyBookings();

  BookingResponse completeBooking(Long bookingId);

}
