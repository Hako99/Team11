package com.project.team11_tabling.global.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopBookingCountDto {
  private Long shopId;
  private int shopBookingCount;
}
