package com.project.team11_tabling.domain.shop;

import com.project.team11_tabling.domain.shop.externalAPI.KakaoResponseDTO;

public interface ShopService {

  KakaoResponseDTO getAPI(String search);

  ShopResponseDto registerShop(ShopRequestDto requestDto);


}
