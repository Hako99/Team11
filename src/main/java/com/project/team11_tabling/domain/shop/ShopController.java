package com.project.team11_tabling.domain.shop;


import com.project.team11_tabling.domain.shop.externalAPI.KakaoResponseDTO;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private final ShopServiceImpl shopService;

  @GetMapping
  public ResponseEntity<CommonResponse<KakaoResponseDTO>> searchKeyword(
      @RequestParam(value = "search", required = false) String search) {

    KakaoResponseDTO responseDTO = shopService.getAPI(search);
    return ResponseEntity.status(HttpStatus.OK.value())
        .body(CommonResponse.<KakaoResponseDTO>builder()
            .msg("식당이 조회되었습니다.")
            .data(responseDTO)
            .build());

  }

}
