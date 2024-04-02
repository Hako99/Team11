package com.project.team11_tabling.domain.booking.service;

import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import com.project.team11_tabling.domain.booking.repository.BookingRepository;
import com.project.team11_tabling.domain.shop.ShopRepository;
import com.project.team11_tabling.domain.shop.entity.ShopSeats;
import com.project.team11_tabling.domain.shop.repository.ShopSeatsRepository;
import com.project.team11_tabling.global.exception.custom.NotFoundException;
import com.project.team11_tabling.global.exception.custom.UserNotMatchException;
import com.project.team11_tabling.global.jwt.security.UserDetailsImpl;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;
  private final ShopRepository shopRepository;
  private final ShopSeatsRepository shopSeatsRepository;

  @Override
  public BookingResponse booking(BookingRequest request, UserDetailsImpl userDetails) {
    shopRepository.findById(request.getShopId())
        .orElseThrow(() -> new NotFoundException("식당 정보가 없습니다."));

    Long lastTicketNumber = bookingRepository.findLastTicketNumberByShopId(request.getShopId());
    ShopSeats shopSeats = shopSeatsRepository.findByShopId(request.getShopId());

    Booking booking;
    if (shopSeats.getAvailableSeats() > 0) {
      shopSeats.removeSeats();
      shopSeatsRepository.saveAndFlush(shopSeats);

      booking = Booking.of(request, lastTicketNumber, userDetails.getUserId(), BookingType.DONE);
    } else {
      booking = Booking.of(request, lastTicketNumber, userDetails.getUserId(), BookingType.WAITING);
      // TODO: addQueue
    }

    Booking saveBooking = bookingRepository.save(booking);

    return new BookingResponse(saveBooking);
  }

  @Override
  public BookingResponse cancelBooking(Long bookingId, UserDetailsImpl userDetails) {

    Booking booking = findBooking(bookingId);

    validateBookingUser(booking.getUserId(), userDetails.getUserId());

    booking.cancelBooking();
    return new BookingResponse(bookingRepository.saveAndFlush(booking));
  }

  @Override
  @Transactional(readOnly = true)
  public List<BookingResponse> getMyBookings(UserDetailsImpl userDetails) {

    List<Booking> myBookings = bookingRepository.findByUserId(userDetails.getUserId());

    return myBookings.stream()
        .map(BookingResponse::new)
        .toList();
  }

  @Override
  public BookingResponse noShow(Long bookingId, UserDetailsImpl userDetails) {
    Booking booking = findBooking(bookingId);

    validateBookingUser(booking.getUserId(), userDetails.getUserId());

    booking.noShow();
    return new BookingResponse(bookingRepository.saveAndFlush(booking));
  }

  // TODO: DONE 전용 이벤트 메소드

  private Booking findBooking(Long bookingId) {
    return bookingRepository.findById(bookingId)
        .orElseThrow(() -> new NotFoundException("줄서기 정보가 없습니다."));
  }

  private void validateBookingUser(Long bookingUserId, Long userId) {
    if (!Objects.equals(bookingUserId, userId)) {
      throw new UserNotMatchException("예약자만 취소할 수 있습니다.");
    }
  }

}
