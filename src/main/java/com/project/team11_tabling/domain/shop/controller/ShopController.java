package com.project.team11_tabling.domain.shop.controller;


import com.project.team11_tabling.domain.shop.service.ShopService;
import com.project.team11_tabling.domain.shop.dto.ShopRequestDto;
import com.project.team11_tabling.domain.shop.dto.ShopResponseDto;
import com.project.team11_tabling.domain.shop.externalAPI.KakaoResponseDTO;
import com.project.team11_tabling.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops")
public class ShopController {
  private final ShopService shopService;

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping
  public ResponseEntity<CommonResponse<KakaoResponseDTO>> searchKeyword(
      @RequestParam(value = "search", required = false) String search) {

    KakaoResponseDTO responseDTO = shopService.getAPI(search);
    return ResponseEntity.status(HttpStatus.OK.value())
        .body(CommonResponse.<KakaoResponseDTO>builder()
            .data(responseDTO)
            .build());

  }

  @PostMapping(consumes = "application/json")
  public Long registerShop(
      @RequestBody ShopRequestDto requestDto) {
    return shopService.registerShop(requestDto);
  }

  @GetMapping("/{shopId}")
  public ResponseEntity<CommonResponse<ShopResponseDto>> getShopInfo(
      @PathVariable Long shopId) {

    ShopResponseDto responseDto = shopService.getShopInfo(shopId);
    return ResponseEntity.status(HttpStatus.OK.value())
        .body(CommonResponse.<ShopResponseDto>builder()
            .data(responseDto)
            .build());

  }



}
