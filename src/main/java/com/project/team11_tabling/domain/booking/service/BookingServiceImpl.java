package com.project.team11_tabling.domain.booking.service;

import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.domain.booking.repository.BookingRepository;
import java.util.List;
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

  @Override
  public void deleteBooking(Long bookingId) {

    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new IllegalArgumentException("없는 예약번호 입니다."));

    bookingRepository.delete(booking);
  }

  @Override
  public List<BookingResponse> getMyBookings() {

    // TODO: auth
    List<Booking> myBookings = bookingRepository.findByUserId(1L);

    return myBookings.stream()
        .map(BookingResponse::new)
        .toList();
  }
}
