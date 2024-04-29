package com.project.team11_tabling.domain.shop.service.redisMessage;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Service
@Slf4j(topic = "waitingService")
@RequiredArgsConstructor
public class RealtimeWaitingDataService {

  private final RedisTemplate<String, String> redisTemplate;
  private final ShopTopicRepository shopTopicRepository;


  public void sendUpdate(Long shopId, Long queueSize, String status) {
    ChannelTopic topic = shopTopicRepository.getTopic(String.valueOf(shopId));

    if(topic==null){
      throw new IllegalArgumentException("topic이 없습니다.");
    }
    log.info("topic : " + topic);
    String jsonMessage = createJsonMessage(String.valueOf(shopId), String.valueOf(queueSize), status);
    redisTemplate.convertAndSend(topic.getTopic(), jsonMessage);
  }


  private String createJsonMessage(String shopId, String message, String status) {
    Map<String, String> map = new HashMap<>();
    map.put("shopId", shopId);
    map.put("message", message);
    map.put("status", status);
    try {
      return new ObjectMapper().writeValueAsString(map);
    } catch (JsonProcessingException e) {
      log.error("Error creating JSON message", e);
      return "{}";
    }
  }



}
