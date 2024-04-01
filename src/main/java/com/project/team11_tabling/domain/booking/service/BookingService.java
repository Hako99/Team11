package com.project.team11_tabling.domain.booking.service;

import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.global.jwt.security.UserDetailsImpl;
import java.util.List;

public interface BookingService {

  BookingResponse booking(BookingRequest request, UserDetailsImpl userDetails);

  BookingResponse cancelBooking(Long bookingId, UserDetailsImpl userDetails);

  List<BookingResponse> getMyBookings(UserDetailsImpl userDetails);

  BookingResponse noShow(Long bookingId, UserDetailsImpl userDetails);

}
