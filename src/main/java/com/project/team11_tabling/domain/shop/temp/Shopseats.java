package com.project.team11_tabling.domain.shop.temp;


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
public class Shopseats {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long shopId;

  private Integer seats;

  private Integer currentSeats; //매장 + 대기고객수

  public static Shopseats of(Long shopId, Integer seats) {
    return Shopseats.builder()
        .shopId(shopId)
        .seats(seats)
        .build();
  }

  public void decreaseSeat(){
    this.currentSeats = currentSeats-1;
  }

  public void increaseSeat(){this.currentSeats = currentSeats+1;
  }

}