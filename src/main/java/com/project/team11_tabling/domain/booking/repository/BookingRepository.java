package com.project.team11_tabling.domain.booking.repository;

import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long>, BookingRepositoryQuery {

  List<Booking> findByUserId(Long userId);
  Optional<Set<Booking>> findByUserIdAndShopIdAndState(Long userId, Long shopId, BookingType type);

}
