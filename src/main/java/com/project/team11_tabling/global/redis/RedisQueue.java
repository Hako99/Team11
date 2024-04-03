package com.project.team11_tabling.global.redis;

import com.project.team11_tabling.global.event.CallingEvent;
import com.project.team11_tabling.global.event.DoneEvent;
import com.project.team11_tabling.global.event.WaitingEvent;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class RedisQueue {

  private final StringRedisTemplate redisTemplate;
  private final ApplicationEventPublisher eventPublisher;

  @TransactionalEventListener
  public void addQueue(WaitingEvent bookingDto) {
    Long shopId = bookingDto.getShopId();
    Long userId = bookingDto.getUserId();
    redisTemplate.opsForList().rightPush("shop-" + shopId, String.valueOf(userId));
  }

  @EventListener
  @Async
  public void popQueue(CallingEvent callingDto) {
    // keys * 해서userId를 넘김 LIst,
    // popQueue -> 모든 줄서기가 있는 가게에서 왼쪽에서 한명 씩 다 뺀다.
    // 이거를 유저아이디를 리스트로 만들어서 알림 서비스에 전달
    // 스케줄러에서는 그냥 10분마다 popQueue 이벤트를 발생시키기만 하면 끝

    Set<String> keys = redisTemplate.keys("shop-*");

    if (keys != null && keys.size() > 0) {
      keys.stream()
          .filter(key -> {
            Long size = redisTemplate.opsForList().size(key);
            return size != null && size > 0;
          })
          .map(key -> {
            String userId = redisTemplate.opsForList().leftPop(key);
            return new DoneEvent(Long.parseLong(key), Long.parseLong(userId));
          })
          .forEach(eventPublisher::publishEvent);
    }

  }

}
