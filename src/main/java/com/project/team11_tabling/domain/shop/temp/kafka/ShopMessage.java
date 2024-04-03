package com.project.team11_tabling.domain.shop.temp.kafka;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMessage {
  Long shopId;
  String message;

  public ShopMessage(Long shopId, String text){
    this.shopId = shopId;
    this.message = text;
  }

}
