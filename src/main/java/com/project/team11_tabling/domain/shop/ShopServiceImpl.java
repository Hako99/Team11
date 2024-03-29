package com.project.team11_tabling.domain.shop;

import com.project.team11_tabling.domain.shop.externalAPI.KakaoAPI;
import com.project.team11_tabling.domain.shop.externalAPI.KakaoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl {

  private static final String USER_CACHE = "userCache";
  private final KakaoAPI kakaoAPI;
  private final ShopRepository shopRepository;

  @Cacheable(value = USER_CACHE, key = "#search")
  public KakaoResponseDTO getAPI(String search) {
    KakaoResponseDTO response = kakaoAPI.getAPI(search);
    return response;
  }

}
