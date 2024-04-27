package com.project.team11_tabling.domain.booking.repository;

import com.project.team11_tabling.domain.booking.entity.Booking;
import java.util.Optional;

public interface BookingRepositoryQuery {

  Long findLastTicketNumberByShopId(Long shopId);

  Optional<Booking> findByShopIdAndUserId(Long shopId, Long userId);

  Optional<Booking> findBookingByUserId(Long userId);



}
