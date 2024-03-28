package com.project.team11_tabling.domain.booking.service;

import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.domain.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;

  @Override
  public BookingResponse booking(BookingRequest request) {

    // TODO: ticketing, status, user
    Booking booking = Booking.of(request);

    return new BookingResponse(bookingRepository.save(booking));
  }

}
