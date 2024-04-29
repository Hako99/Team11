package com.project.team11_tabling.domain.shop.service;

import com.project.team11_tabling.domain.shop.dto.ShopRequestDto;
import com.project.team11_tabling.domain.shop.dto.ShopResponseDto;
import com.project.team11_tabling.domain.shop.entity.Shop;
import com.project.team11_tabling.domain.shop.entity.ShopSeats;
import com.project.team11_tabling.domain.shop.externalAPI.KakaoAPI;
import com.project.team11_tabling.domain.shop.externalAPI.KakaoResponseDTO;
import com.project.team11_tabling.domain.shop.repository.ShopRepository;
import com.project.team11_tabling.domain.shop.repository.ShopSeatsRepository;
import com.project.team11_tabling.global.redis.WaitingQueueService;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "RedisListener")
public class ShopServiceImpl implements ShopService {

  private static final String USER_CACHE = "userCache";
  private final KakaoAPI kakaoAPI;
  private final ShopRepository shopRepository;
  private final ShopSeatsRepository shopSeatsRepository;
  private final WaitingQueueService waitingQueueService;

  @Cacheable(value = USER_CACHE, key = "#search")
  public KakaoResponseDTO getAPI(String search) {
    KakaoResponseDTO response = kakaoAPI.getAPI(search);
    return response;
  }

  public Long registerShop(ShopRequestDto requestDto) {
    Optional<Shop> exist = shopRepository.findByShopId(requestDto.getId());
    if(exist.isPresent()){
      return exist.get().getId();
    }
    Shop shop = new Shop(requestDto);
    shop.updateTime(randomTime());

    Shop saveShop = shopRepository.save(shop);
    shopSeatsRepository.save(ShopSeats.of(saveShop.getId(),0));

    return saveShop.getId(); //shop의 id 리턴
  }

  public LocalTime[] randomTime() {
    LocalTime[] date = new LocalTime[2];
    Random random = new Random();

    int openHour = random.nextInt(7, 12);
    int closeHour = random.nextInt(17, 23);

    LocalTime randomTime1 = LocalTime.of(openHour, 0);
    LocalTime randomTime2 = LocalTime.of(closeHour, 0);

    randomTime1.withSecond(0).withNano(0);
    randomTime2.withSecond(0).withNano(0);

    date[0] = randomTime1;
    date[1] = randomTime2;

    return date;
  }

  public Integer randomSeat() {
    Random random = new Random();
    return random.nextInt(10, 30);
  }

  public ShopResponseDto getShopInfo(Long id) {
    Shop shop = shopRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당가게 정보가 없습니다."));
    ShopResponseDto responseDto = new ShopResponseDto(shop);
    responseDto.updateWaitingNum(waitingQueueService.getWaitingQueueSize(id));
    System.out.println(responseDto.getWaitingNum());
    return responseDto;
  }

  @Override
  public List<ShopResponseDto> getPopularShop() {
    List<Shop> popularShops = shopRepository.findByPopularShopOrderByReviewCountDesc(true);

    return popularShops.stream()
        .map(ShopResponseDto::new)
        .peek(shop -> shop.updateWaitingNum(waitingQueueService.getWaitingQueueSize(shop.getId())))
        .toList();
  }

}
