package com.project.team11_tabling.domain.booking.service;

import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import com.project.team11_tabling.global.jwt.security.UserDetailsImpl;
import java.util.List;

public interface BookingService {

  BookingResponse booking(BookingRequest request, UserDetailsImpl userDetails);

  BookingResponse cancelBooking(Long bookingId);

  List<BookingResponse> getMyBookings();

  BookingResponse completeBooking(Long bookingId, BookingType type);

}
