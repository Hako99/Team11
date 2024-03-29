package com.project.team11_tabling.domain.booking.repository;

public interface BookingRepositoryQuery {

  Long findLastTicketNumberByShopId(Long shopId);

}
