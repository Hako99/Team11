package com.project.team11_tabling.domain.booking.repository;

import static com.project.team11_tabling.domain.booking.entity.QBooking.booking;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookingRepositoryQueryImpl implements BookingRepositoryQuery {

  private final JPAQueryFactory factory;

  @Override
  public Long findLastTicketNumberByShopId(Long shopId) {

    StringTemplate stringTemplate = Expressions.stringTemplate(
        "DATE_FORMAT({0}, {1})"
        , booking.reservedDatetime
        , ConstantImpl.create("%Y-%m-%d")
    );

    Long lastTicketNumber = factory.select(booking.ticketNumber)
        .from(booking)
        .where(
            booking.shopId.eq(shopId)
                .and(stringTemplate.eq(String.valueOf(LocalDate.now())))
        )
        .orderBy(booking.ticketNumber.desc())
        .limit(1)
        .fetchFirst();

    return lastTicketNumber != null ? lastTicketNumber + 1 : 1;
  }

}
