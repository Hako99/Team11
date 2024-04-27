package com.project.team11_tabling.domain.shop.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j(topic = "RedisListener")
public class RealtimeWaitingDataService {

  private final ConcurrentHashMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();
  private final RedisTemplate<String, String> redisTemplate;
  private final ChannelTopic topic; //channel

  public RealtimeWaitingDataService(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.topic = new ChannelTopic("waitingUpdates");
  }

  public SseEmitter addEmitter(String clientId) {
    final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
    emitters.put(clientId, emitter);

    emitter.onCompletion(() -> emitters.remove(clientId));
    emitter.onTimeout(() -> emitters.remove(clientId));

    return emitter;
  }


  // redis 메시지 전송 메소드 업데이트
  public void sendUpdate(Long shopId, Long queueSize, String status) {
    String jsonMessage = createJsonMessage(String.valueOf(shopId), String.valueOf(queueSize), status);
    log.info("send message : " + shopId + "의 대기열 수 : " + queueSize + " 상태 : " + status);
    redisTemplate.convertAndSend(topic.getTopic(), jsonMessage);
  }

  //basic listener
  @Transactional
  public void handleMessage(String message) {
    log.info("subscribe message");
    ObjectMapper mapper = new ObjectMapper();
    SseEmitter emitter = null;
    String shopId = "";
    String actualMessage = "";
    String status = "";

    //read message
    try {
      Map<String, String> map = mapper.readValue(message, new TypeReference<Map<String, String>>() {
      });
      shopId = map.get("shopId");
      actualMessage = map.get("message");
      status = map.get("status");
    } catch (IOException e) {
      System.out.println("Error getting message: " + e.getMessage());
    }

    emitter = emitters.get(shopId); // emitter 가져오기


    if(emitter==null){
      emitter = addEmitter(shopId);
      log.info("emitter 추가" + emitter);
    }
    sendToEmitter(emitter, actualMessage,status);
  }

  public static void sendToEmitter(SseEmitter emitter, String actualMessage, String status) {
    if (emitter != null) {
      CompletableFuture.runAsync(() -> {
        try {
          log.info("status : " + status);
          emitter.send(SseEmitter.event()
              .data(actualMessage, org.springframework.http.MediaType.APPLICATION_JSON));
          // 확인: 메시지 전송 후 상태가 "ok"인 경우에만 완료
          if ("ok".equals(status)) {
            // CompletableFuture를 사용하여 비동기 작업 완료 후 호출
            CompletableFuture.runAsync(emitter::complete);
          }
        } catch (IOException e) {
          log.error("Error sending event to emitter: {}", e.getMessage());
        }
      });
    } else {
      log.info("No emitter found");
    }
  }

  public String createJsonMessage(String shopId, String message, String status) {
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map = new HashMap<>();
    map.put("shopId", shopId);
    map.put("message", message);
    map.put("status", status);
    String jsonMessage = null;
    try {
      jsonMessage = mapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return jsonMessage;
  }

}
