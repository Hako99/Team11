package com.project.team11_tabling.domain.booking.service;

import com.project.team11_tabling.domain.alarm.service.AlarmService;
import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import com.project.team11_tabling.domain.booking.repository.BookingRepository;
import com.project.team11_tabling.domain.shop.ShopRepository;
import com.project.team11_tabling.domain.shop.entity.ShopSeats;
import com.project.team11_tabling.domain.shop.repository.ShopSeatsRepository;
import com.project.team11_tabling.global.event.AlarmEvent;
import com.project.team11_tabling.global.event.DoneEvent;
import com.project.team11_tabling.global.event.WaitingEvent;
import com.project.team11_tabling.global.exception.custom.NotFoundException;
import com.project.team11_tabling.global.exception.custom.UserNotMatchException;
import com.project.team11_tabling.global.jwt.security.UserDetailsImpl;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "BookingServiceImpl")
@Service
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;
  private final ShopRepository shopRepository;
  private final ShopSeatsRepository shopSeatsRepository;
  private final AlarmService alarmService;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public SseEmitter booking(BookingRequest request, UserDetailsImpl userDetails) {
    shopRepository.findById(request.getShopId())
        .orElseThrow(() -> new NotFoundException("식당 정보가 없습니다."));

    Long lastTicketNumber = bookingRepository.findLastTicketNumberByShopId(request.getShopId());
    ShopSeats shopSeats = shopSeatsRepository.findByShopId(request.getShopId());

    Booking booking;
    if (shopSeats.getAvailableSeats() > 0) {
      shopSeats.removeAvailableSeats();
      shopSeatsRepository.save(shopSeats);

      booking = Booking.of(request, lastTicketNumber, userDetails.getUserId(), BookingType.DONE);
    } else {
      booking = Booking.of(request, lastTicketNumber, userDetails.getUserId(), BookingType.WAITING);
      eventPublisher.publishEvent(new WaitingEvent(booking.getShopId(), booking.getUserId()));
    }

    bookingRepository.save(booking);
    eventPublisher.publishEvent(new AlarmEvent(booking));
    return alarmService.subscribe(userDetails.getUserId());
  }

  @Override
  public BookingResponse cancelBooking(Long bookingId, UserDetailsImpl userDetails) {

    Booking booking = findBooking(bookingId);

    validateBookingUser(booking.getUserId(), userDetails.getUserId());

    booking.cancelBooking();
    eventPublisher.publishEvent(new AlarmEvent(booking));
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
    eventPublisher.publishEvent(new AlarmEvent(booking));
    return new BookingResponse(bookingRepository.saveAndFlush(booking));
  }

  @Async
  @TransactionalEventListener
  public void doneBooking(DoneEvent doneEvent) {
    log.info("doneBookingEvent:: shopId = {}, userId = {}",
        doneEvent.getShopId(), doneEvent.getUserId());

    Booking booking = bookingRepository.findByShopIdAndUserId(
            doneEvent.getShopId(), doneEvent.getUserId())
        .orElseThrow(() -> new NotFoundException("잘못된 줄서기 정보입니다."));

    ShopSeats shopSeats = shopSeatsRepository.findByShopId(doneEvent.getShopId());
    shopSeats.removeAvailableSeats();
    shopSeatsRepository.save(shopSeats);

    booking.doneBooking();
    eventPublisher.publishEvent(new AlarmEvent(booking));
    bookingRepository.save(booking);
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
