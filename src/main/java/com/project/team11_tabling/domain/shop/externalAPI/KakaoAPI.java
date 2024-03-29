package com.project.team11_tabling.domain.shop.externalAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class KakaoAPI {

  private final RestTemplate restTemplate;
  @Value("${kakao_id}")
  private String kakao_apikey;

  public KakaoResponseDTO getAPI(String search) {
    final HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "KakaoAK " + kakao_apikey);

    String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json?" +
        "query=" + search//query
        + "&category_group_code=" + "FD6";

    final HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<KakaoResponseDTO> response  = restTemplate.exchange(apiURL, HttpMethod.GET, entity, KakaoResponseDTO.class);
    return response.getBody();
  }

}
