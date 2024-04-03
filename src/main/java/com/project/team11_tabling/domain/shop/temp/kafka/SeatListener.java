package com.project.team11_tabling.domain.shop.temp.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.team11_tabling.domain.shop.temp.Shopseats;
import com.project.team11_tabling.domain.shop.temp.ShopseatsRepository;
import com.project.team11_tabling.domain.shop.temp.WaitingQueue;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Component
public class SeatListener {

  private final WaitingQueue queue;
  private final ShopseatsRepository shopseatsRepository;
  private final ObjectMapper objectMapper;

//  @KafkaListener(topics = "waiting", groupId = "group_id")
//  public void listenShopWaiting(String jsonMessage) {
//    System.out.println("Received message: " + jsonMessage);
//    try {
//      ShopMessage message = objectMapper.readValue(jsonMessage, ShopMessage.class);
//      String shopId = String.valueOf(message.getShopId());
//      System.out.println("현재 대기자 수: " + queue.queueSize(shopId));
//
//    } catch (JsonProcessingException e) {
//      e.getMessage();
//    }
//
//
//  }

  @KafkaListener(topics = "enter", groupId = "group_id")
  public void listenShopEnter(String jsonMessage) {
    System.out.println("Received message: " + jsonMessage);
    try {
      ShopMessage message;
      message = objectMapper.readValue(jsonMessage, ShopMessage.class);
      Long shopId = message.getShopId();
      Optional<Shopseats> shop = shopseatsRepository.findByShopId(shopId);
      //queue에 손님이 있는 경우 pop
      if (queue.queueSize(String.valueOf(shopId)) > 0) {
        queue.popQueue(String.valueOf(shopId));
        enterShop(shopId);

      }
      //queue에 없을 경우 바로 입장
      shop.get().decreaseSeat();
      System.out.println("매장 + 예약 고객 수 : " + shop.get().getCurrentSeats() + " / 가게 좌석 수 :" + shop.get().getSeats());
    } catch (JsonProcessingException e) {
      e.getMessage();
    }

  }

  public void enterShop(Long storeId) {
    Optional<Shopseats> shopseat = shopseatsRepository.findByShopId(storeId);
    shopseat.get().increaseSeat();
  }

}
