package com.project.team11_tabling.domain.booking.repository;

import com.project.team11_tabling.domain.booking.entity.Booking;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long>, BookingRepositoryQuery {

  List<Booking> findByUserId(Long userId);

  Optional<Booking> findByShopIdAndUserId(Long shopId, Long Userid);

}
