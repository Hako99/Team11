package com.project.team11_tabling.global.batch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopBatchDto {

  private Long shopId;
  private String name;
  private String address;
  private String city;
  private String phone;
  private Integer reviewCount;
  private String openTime;
  private String closeTime;
}
