package com.project.team11_tabling.domain.shop.externalAPI;


import java.io.Serializable;
import lombok.Data;

@Data
public class KakaoResponseDTO implements Serializable {

  public Document[] documents;

  @Data
  static class Document implements Serializable {

    public String id;
    public String place_name;
    public String address_name;
    public String phone;

  }
}
