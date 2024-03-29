package com.project.team11_tabling.domain.shop.externalAPI;


import lombok.Data;

@Data
public class KakaoResponseDTO {

  public Document[] documents;

  @Data
  static class Document {

    public String id;
    public String place_name;
    public String category_name;
    public String address_name;
    public String phone;

  }
}
