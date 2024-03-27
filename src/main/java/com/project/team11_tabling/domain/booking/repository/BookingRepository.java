package com.project.team11_tabling.domain.booking.repository;

import com.project.team11_tabling.domain.booking.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
