package com.project.team11_tabling.domain.booking.repository;

import static com.project.team11_tabling.domain.booking.entity.QBooking.booking;

import com.project.team11_tabling.domain.booking.entity.Booking;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookingRepositoryQueryImpl implements BookingRepositoryQuery {

  private final JPAQueryFactory factory;

  @Override
  public Long findLastTicketNumberByShopId(Long shopId) {

    StringTemplate dateStringTemplate = getDateStringTemplate();

    Long lastTicketNumber =
        factory.select(booking.ticketNumber)
            .from(booking)
            .where(
                booking.shopId.eq(shopId)
                    .and(dateStringTemplate.eq(String.valueOf(LocalDate.now())))
            )
            .orderBy(booking.ticketNumber.desc())
            .limit(1)
            .fetchFirst();

    return lastTicketNumber != null ? lastTicketNumber + 1 : 1;
  }

  @Override
  public Optional<Booking> findByShopIdAndUserId(Long shopId, Long userId) {

    StringTemplate dateStringTemplate = getDateStringTemplate();
    Booking findBooking =
        factory.select(booking)
            .from(booking)
            .where(
                booking.state.eq(BookingType.WAITING)
                    .and(booking.userId.eq(userId))
                    .and(booking.shopId.eq(shopId))
                    .and(dateStringTemplate.eq(String.valueOf(LocalDate.now())))
            )
            .fetchFirst();

    return findBooking == null ? Optional.empty() : Optional.of(findBooking);
  }

  @Override
  public Optional<Booking> findBookingByUserId(Long userId) {
    StringTemplate dateStringTemplate = getDateStringTemplate();

    Booking findBooking =
        factory.select(booking)
            .from(booking)
            .where(
                booking.state.eq(BookingType.WAITING)
                    .and(booking.userId.eq(userId))
                    .and(dateStringTemplate.eq(String.valueOf(LocalDate.now())))
            )
            .fetchFirst();

    return findBooking == null ? Optional.empty() : Optional.of(findBooking);
  }



  private static StringTemplate getDateStringTemplate() {
    return Expressions.stringTemplate(
        "DATE_FORMAT({0}, {1})",
        booking.reservedDatetime,
        ConstantImpl.create("%Y-%m-%d")
    );
  }

}
