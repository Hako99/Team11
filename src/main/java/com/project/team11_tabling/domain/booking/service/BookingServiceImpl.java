package com.project.team11_tabling.domain.booking.service;

import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import com.project.team11_tabling.domain.booking.repository.BookingRepository;
import com.project.team11_tabling.domain.shop.ShopRepository;
import com.project.team11_tabling.global.exception.custom.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;
  private final ShopRepository shopRepository;

  @Override
  public BookingResponse booking(BookingRequest request) {

    shopRepository.findById(request.getShopId())
        .orElseThrow(() -> new NotFoundException("식당 정보가 없습니다."));

    // TODO: user
    Long lastTicketNumber = bookingRepository.findLastTicketNumberByShopId(request.getShopId());
    Booking booking = Booking.of(request, lastTicketNumber);

    return new BookingResponse(bookingRepository.save(booking));
  }

  @Override
  public BookingResponse cancelBooking(Long bookingId) {

    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new NotFoundException("줄서기 정보가 없습니다."));

    booking.cancelBooking();
    return new BookingResponse(bookingRepository.saveAndFlush(booking));
  }

  @Override
  @Transactional(readOnly = true)
  public List<BookingResponse> getMyBookings() {

    // TODO: auth
    List<Booking> myBookings = bookingRepository.findByUserId(1L);

    return myBookings.stream()
        .map(BookingResponse::new)
        .toList();
  }

  @Override
  public BookingResponse completeBooking(Long bookingId, BookingType type) {

    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new NotFoundException("줄서기 정보가 없습니다."));

    booking.completeBooking(type);

    return new BookingResponse(bookingRepository.saveAndFlush(booking));
  }

}
