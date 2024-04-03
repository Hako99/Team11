package com.project.team11_tabling.domain.shop.temp.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.team11_tabling.domain.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class kafkaProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  public kafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void create() {
    kafkaTemplate.send("topic", "say hello~");
  }

  public void sendEnterMessage(Long shopId, String text) {
    String jsonMessage;
    ShopMessage message = new ShopMessage(shopId, text);
    try {
      jsonMessage = objectMapper.writeValueAsString(message);
      kafkaTemplate.send("enter", "seat", jsonMessage);
    } catch(JsonProcessingException e){
      e.getMessage();
    }
  }

//  public void sendWaitingMessage(Long shopId, String text) {
//    String jsonMessage;
//    Long tempUserId = 1L;
//    ShopMessage message = new ShopMessage(shopId, tempUserId, text);
//    try {
//      jsonMessage = objectMapper.writeValueAsString(message);
//      kafkaTemplate.send("waiting", "seat", jsonMessage);
//    } catch(JsonProcessingException e){
//      e.getMessage();
//    }
//  }
}
