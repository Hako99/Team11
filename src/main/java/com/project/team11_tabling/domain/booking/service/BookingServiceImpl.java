package com.project.team11_tabling.domain.booking.service;

import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.repository.BookingRepository;
import com.project.team11_tabling.domain.shop.ShopRepository;
import com.project.team11_tabling.global.event.BookingEvent;
import com.project.team11_tabling.global.event.CallingEvent;
import com.project.team11_tabling.global.exception.custom.NotFoundException;
import com.project.team11_tabling.global.exception.custom.UserNotMatchException;
import com.project.team11_tabling.global.jwt.security.UserDetailsImpl;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;
  private final ShopRepository shopRepository;
  private final ApplicationEventPublisher publisher;

  @Override
  public BookingResponse booking(BookingRequest request, UserDetailsImpl userDetails) {
    //TODO: 좌석 계산해서 남아있다면 바로 done 없다면 queue 추가
    shopRepository.findById(request.getShopId())
        .orElseThrow(() -> new NotFoundException("식당 정보가 없습니다."));

    Long lastTicketNumber = bookingRepository.findLastTicketNumberByShopId(request.getShopId());
    Booking booking = Booking.of(request, lastTicketNumber, userDetails.getUserId());

    Booking saveBooking = bookingRepository.save(booking);

    publisher.publishEvent(new BookingEvent(saveBooking.getShopId(), saveBooking.getUserId()));
    publisher.publishEvent(new CallingEvent(saveBooking.getShopId()));

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
