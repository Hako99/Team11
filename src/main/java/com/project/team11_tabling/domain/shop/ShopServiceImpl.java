package com.project.team11_tabling.domain.shop;

import com.project.team11_tabling.domain.shop.entity.ShopSeats;
import com.project.team11_tabling.domain.shop.externalAPI.KakaoAPI;
import com.project.team11_tabling.domain.shop.externalAPI.KakaoResponseDTO;
import com.project.team11_tabling.domain.shop.repository.ShopSeatsRepository;
import java.time.LocalTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

  private static final String USER_CACHE = "userCache";
  private final KakaoAPI kakaoAPI;
  private final ShopRepository shopRepository;
  private final ShopSeatsRepository shopSeatsRepository;

  @Cacheable(value = USER_CACHE, key = "#search")
  public KakaoResponseDTO getAPI(String search) {
    KakaoResponseDTO response = kakaoAPI.getAPI(search);
    return response;
  }

  public ShopResponseDto registerShop(ShopRequestDto requestDto) {
    Shop shop = new Shop(requestDto);
    shop.updateTime(randomTime());

    Shop saveShop = shopRepository.save(shop);
    shopSeatsRepository.save(ShopSeats.of(saveShop.getId(), randomSeat()));

    return new ShopResponseDto(shop);
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

}
