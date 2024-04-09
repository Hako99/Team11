package com.project.team11_tabling.domain.shop.controller;


import com.project.team11_tabling.domain.shop.service.ShopService;
import com.project.team11_tabling.domain.shop.dto.ShopRequestDto;
import com.project.team11_tabling.domain.shop.dto.ShopResponseDto;
import com.project.team11_tabling.domain.shop.externalAPI.KakaoResponseDTO;
import com.project.team11_tabling.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping
  public ResponseEntity<CommonResponse<KakaoResponseDTO>> searchKeyword(
      @RequestParam(value = "search", required = false) String search) {

    KakaoResponseDTO responseDTO = shopService.getAPI(search);
    return ResponseEntity.status(HttpStatus.OK.value())
        .body(CommonResponse.<KakaoResponseDTO>builder()
            .data(responseDTO)
            .build());

  }

  @PostMapping
  public ResponseEntity<CommonResponse<ShopResponseDto>> registerShop(
      @RequestBody ShopRequestDto requestDto) {

    ShopResponseDto responseDto = shopService.registerShop(requestDto);
    return ResponseEntity.status(HttpStatus.OK.value())
        .body(CommonResponse.<ShopResponseDto>builder()
            .data(responseDto)
            .build());

  }

}
