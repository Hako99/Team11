package com.project.team11_tabling.domain.shop.temp;

import com.project.team11_tabling.domain.shop.temp.kafka.kafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SeatScheduler {

  private final ShopseatsRepository repository;
  private final kafkaProducer producer;


  @Scheduled(fixedRate = 10000) //15초, 싱글쓰레드
  private void schedule1(){
    Long shopId = 1L;
    Shopseats shop = findShop(shopId);
    if(shop.getCurrentSeats()>0 && shop.getCurrentSeats()>shop.getSeats()){
        producer.sendEnterMessage(shopId, "손님이 입장되었습니다");
    }
    else { //가게 좌석 수 이하로 가면 테이블링 서비스 이용 못함
      System.out.println(
          "현재 매장고객 수 : " + shop.getCurrentSeats() + " / 가게 좌석 수 :" + shop.getSeats());
    }
  }


  private Shopseats findShop(Long shopId){
    return repository.findByShopId(shopId).orElseThrow(()-> new IllegalArgumentException("해당 가게 정보가 없습니다."));
  }

}
