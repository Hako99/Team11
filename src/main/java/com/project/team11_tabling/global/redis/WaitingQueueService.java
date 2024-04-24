package com.project.team11_tabling.global.redis;

import com.project.team11_tabling.global.event.CallingEvent;
import com.project.team11_tabling.global.event.CancelEvent;
import com.project.team11_tabling.global.event.DoneEvent;
import com.project.team11_tabling.global.event.WaitingEvent;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Slf4j(topic = "WaitingQueueService")
@Component
public class WaitingQueueService {

  private final StringRedisTemplate redisTemplate;
  private final ApplicationEventPublisher eventPublisher;
  private static final String WAITING_QUEUE_SUFFIX = "-shop";

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void addWaitingQueue(WaitingEvent waitingEvent) {
    Long shopId = waitingEvent.getShopId();
    Long userId = waitingEvent.getUserId();

    log.info("addWaitingQueue:: shopId = {}, userId = {}", shopId, userId);

    redisTemplate.opsForList().rightPush(shopId + WAITING_QUEUE_SUFFIX, String.valueOf(userId));
  }

  @EventListener
  public void popWaitingQueue(CallingEvent callingDto) {
    log.info("popWaitingQueue");

    Set<String> keys = redisTemplate.keys("*" + WAITING_QUEUE_SUFFIX);

    if (keys != null && keys.size() > 0) {
      keys.stream()
          .filter(key -> {
            Long size = redisTemplate.opsForList().size(key);
            return size != null && size > 0;
          })
          .map(key -> {
            String userId = redisTemplate.opsForList().leftPop(key);
            String[] shopKey = key.split("-");
            return new DoneEvent(Long.parseLong(shopKey[0]), Long.parseLong(userId));
          })
          .forEach(eventPublisher::publishEvent);
    }
  }

  @TransactionalEventListener
  public void removeWaitingQueue(CancelEvent cancelEvent) {
    Long shopId = cancelEvent.getShopId();
    Long userId = cancelEvent.getUserId();

    log.info("removeWaitingQueue:: shopId = {}, userId = {}", shopId, userId);

    redisTemplate.opsForList()
        .remove(shopId + WAITING_QUEUE_SUFFIX, 0, String.valueOf(userId));
  }

  public Long getWaitingQueueSize(Long shopId) {
    Long size = redisTemplate.opsForList()
        .size(shopId + WAITING_QUEUE_SUFFIX);

    return size == null ? 0 : size;
  }

}
