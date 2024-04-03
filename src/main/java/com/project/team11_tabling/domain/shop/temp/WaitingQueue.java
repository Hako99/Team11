package com.project.team11_tabling.domain.shop.temp;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WaitingQueue {

  private final ShopseatsRepository repository;
  private final RedissonClient redissonClient;
  private final RedisTemplate<String, String> redisTemplate;

  public WaitingQueue(ShopseatsRepository repository, RedissonClient redissonClient, RedisTemplate<String, String> redisTemplate) {
    this.repository = repository;
    this.redissonClient = redissonClient;
    this.redisTemplate = redisTemplate;
  }

  @Transactional
  public void addQueue(String shopId, String customerId) {
    String lockName = "lock:shop:" + shopId;
    RLock lock = redissonClient.getLock(lockName);

    try {
      boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
      if (isLocked) {
        // 락 획득
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        Long position = listOps.leftPush(shopId, customerId);
        Optional<Shopseats> shop = repository.findByShopId(Long.valueOf(shopId));
        if (shop.isPresent()) {
          shop.get().increaseSeat();
          System.out.println("웨이팅이 등록되었습니다!");
          System.out.println("등록고객ID : " + customerId + " 현재 대기자 수 : " + position + "명 입니다");
        } else {
          System.out.println("매장을 찾을 수 없습니다.");
        }
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      lock.unlock();
    }
  }

  public void popQueue(String shopId) {
    String lockName = "lock:shop:" + shopId;
    RLock lock = redissonClient.getLock(lockName);

    try {
      boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
      if (isLocked) {
        // 락 획득
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        String customerId = listOps.rightPop(shopId);
        if (customerId != null) {
          System.out.println("(queue) " + customerId + "님 지금 입장해주세요!");
          Optional<Shopseats> shop = repository.findByShopId(Long.valueOf(shopId));
          if (shop.isPresent()) {
            shop.get().decreaseSeat();
          } else {
            System.out.println("매장을 찾을 수 없습니다.");
          }
        } else {
          System.out.println("empty queue.");
        }
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
        lock.unlock();
    }
  }

  public Long queueSize(String storeId){
    ListOperations<String, String> listOps = redisTemplate.opsForList();
    return listOps.size(storeId);
  }
}
