package com.project.team11_tabling.domain.shop.service.redisMessage;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Service
@Slf4j(topic = "RedisListener")

public class RedisSubscriber implements MessageListener{

  private SseEmitter emitter; // connect emitter
  private final List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>());
  public SseEmitter addEmitter() {
    emitter = new SseEmitter(Long.MAX_VALUE);
    emitters.add(emitter);
    return emitter;
  }

  public void onMessage(Message message, byte[] pattern) {
    log.info("Handling message: {}", message);
    String payload = new String(message.getBody(), StandardCharsets.UTF_8);
    String actualMessage = "";
    ObjectMapper mapper = new ObjectMapper();
    try {
      Map<String, String> map = mapper.readValue(payload, new TypeReference<Map<String, String>>() {
      });
      String shopId = map.get("shopId");
      actualMessage = map.get("message");
    } catch (IOException e) {
      log.error("Error processing message", e);
    }

    List<SseEmitter> snapshotEmitters = new ArrayList<>(emitters);
    log.info(String.valueOf(snapshotEmitters.size()));
    for (SseEmitter emitter : snapshotEmitters) {
      try {
        emitter.send(SseEmitter.event().data(actualMessage, MediaType.APPLICATION_JSON));
      } catch (IOException e) {
        emitters.remove(emitter);
        emitter.completeWithError(e);
      }
    }
  }
}
