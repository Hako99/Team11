package com.project.team11_tabling.domain.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ShopSeats {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long shopId;

  private Integer seats;

  private Integer availableSeats;

  public static ShopSeats of(Long shopId, Integer seats) {
    return ShopSeats.builder()
        .shopId(shopId)
        .seats(seats)
        .build();
  }

  public void addAvailableSeat() {
    this.availableSeats ++;
  }
}
