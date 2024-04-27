package com.project.team11_tabling.domain.booking.repository;

import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import java.util.Optional;
import java.util.Set;

public interface BookingRepositoryQuery {

  Long findLastTicketNumberByShopId(Long shopId);

  Optional<Booking> findByShopIdAndUserId(Long shopId, Long userId);

  Optional<Booking> findBookingByUserId(Long userId);
  Optional<Set<Booking>> findByUserIdAndShopIdAndState(Long userId,Long shopId, BookingType DONE);



}
