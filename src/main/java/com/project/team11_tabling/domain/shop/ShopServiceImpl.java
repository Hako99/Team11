package com.project.team11_tabling.domain.shop;

import com.project.team11_tabling.domain.shop.externalAPI.KakaoAPI;
import com.project.team11_tabling.domain.shop.externalAPI.KakaoResponseDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService{

  private static final String USER_CACHE = "userCache";
  private final KakaoAPI kakaoAPI;
  private final ShopRepository shopRepository;

  @Cacheable(value = USER_CACHE, key = "#search")
  public KakaoResponseDTO getAPI(String search) {
    KakaoResponseDTO response = kakaoAPI.getAPI(search);
    return response;
  }


  public ShopResponseDto registerShop(ShopRequestDto requestDto) {
    Shop shop = new Shop(requestDto);
    shop.setTime(randomTime());
    return new ShopResponseDto(shop);
  }

  public Date[] randomTime(){
    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();
    Date[] date = new Date[2];
    Random random = new Random();

    int openHour = random.nextInt(7,12);
    int closeHour = random.nextInt(17,23);

    calendar1.set(Calendar.HOUR_OF_DAY, openHour);
    calendar2.set(Calendar.HOUR_OF_DAY, closeHour);

    date[0] = calendar1.getTime();
    date[1] = calendar2.getTime();

    return date;
  }
}
