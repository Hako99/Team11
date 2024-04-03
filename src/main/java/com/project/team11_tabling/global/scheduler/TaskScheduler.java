package com.project.team11_tabling.global.scheduler;

import com.project.team11_tabling.domain.shop.entity.ShopSeats;
import com.project.team11_tabling.domain.shop.repository.ShopSeatsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskScheduler {

  private final ShopSeatsRepository shopSeatsRepository;
  @Scheduled(fixedDelay = 600000)
  public void addAvailableSeat() {
    List<ShopSeats> shopSeats = shopSeatsRepository.findAll();
    for (ShopSeats s : shopSeats) {
      if(s.getAvailableSeats() < s.getSeats()) {
        s.addAvailableSeat();
        shopSeatsRepository.save(s);
      }
    }
    log.info(String.valueOf(shopSeats.get(12).getAvailableSeats()));
  }
}
