package com.project.team11_tabling.domain.booking.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;
  private Long shopId;
  private Long ticketNumber;
  private Long state;
  private LocalDateTime reservedAt;
  private Long reservedParty;

}
