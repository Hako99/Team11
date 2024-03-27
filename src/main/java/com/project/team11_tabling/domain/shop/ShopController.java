package com.project.team11_tabling.domain.shop;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops")
public class ShopController {

  @PostMapping
  public ResponseEntity<CommonResponse<ShopResponseDto>> createSchedule(
      @RequestBody ShopResponseDto requestDto) {

    return ResponseEntity.status(HttpStatus.OK.value())
        .body(CommonResponse.<ShopResponseDto>builder()
            .msg("식당이 조회되었습니다.")
            .build());

  }

}
