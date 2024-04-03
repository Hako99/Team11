package com.project.team11_tabling.domain.shop.temp.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class kafkaConsumer {

  @KafkaListener(topics = "topic", groupId = "group_1")
  public void listener(Object data) {
    System.out.println(data);
  }

}
