package com.project.team11_tabling.domain.shop.service;

import com.project.team11_tabling.domain.shop.dto.ShopRequestDto;
import com.project.team11_tabling.domain.shop.dto.ShopResponseDto;
import com.project.team11_tabling.domain.shop.externalAPI.KakaoResponseDTO;

public interface ShopService {

  KakaoResponseDTO getAPI(String search);

  Long registerShop(ShopRequestDto requestDto);


  ShopResponseDto getShopInfo(Long shopId);
}
