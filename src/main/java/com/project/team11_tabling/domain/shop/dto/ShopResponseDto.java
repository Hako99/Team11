package com.project.team11_tabling.domain.shop.dto;


import com.project.team11_tabling.domain.shop.entity.Shop;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ShopResponseDto {

  private Long shopId;
  private String placeName;
  private String city;
  private String address;
  private String phone;
  private LocalTime openTime;
  private LocalTime closeTime;
  private Long waitingNum;

  public ShopResponseDto(Shop shop) {
    this.shopId = shop.getShopId();
    this.placeName = shop.getName();
    this.city = shop.getCity();
    this.address = shop.getAddress();
    this.phone = shop.getPhone();
    this.openTime = shop.getOpenTime();
    this.closeTime = shop.getCloseTime();
  }

  public void updateWaitingNum(Long num){
    this.waitingNum = num;
  }

}
