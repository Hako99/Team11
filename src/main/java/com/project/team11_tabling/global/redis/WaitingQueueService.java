package com.project.team11_tabling.global.redis;

import com.project.team11_tabling.global.event.CallingEvent;
import com.project.team11_tabling.global.event.DoneEvent;
import com.project.team11_tabling.global.event.WaitingEvent;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Slf4j(topic = "WaitingQueueService")
@Component
public class WaitingQueueService {

  private final StringRedisTemplate redisTemplate;
  private final ApplicationEventPublisher eventPublisher;

  @TransactionalEventListener
  public void addWaitingQueue(WaitingEvent waitingEvent) {
    Long shopId = waitingEvent.getShopId();
    Long userId = waitingEvent.getUserId();

    log.info("addWaitingQueue:: shopId = {}, userId = {}", shopId, userId);

    redisTemplate.opsForList().rightPush(shopId + "-shop", String.valueOf(userId));
  }

  @EventListener
  @Async
  public void popWaitingQueue(CallingEvent callingDto) {
    log.info("popWaitingQueue");

    Set<String> keys = redisTemplate.keys("*-shop");

    if (keys != null && keys.size() > 0) {
      keys.stream()
          .filter(key -> {
            Long size = redisTemplate.opsForList().size(key);
            return size != null && size > 0;
          })
          .map(key -> {
            String userId = redisTemplate.opsForList().leftPop(key);
            return new DoneEvent(Long.parseLong(key.substring(0, 1)), Long.parseLong(userId));
          })
          .forEach(eventPublisher::publishEvent);
    }
  }

}
