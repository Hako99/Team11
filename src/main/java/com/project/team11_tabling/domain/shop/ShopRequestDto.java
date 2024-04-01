package com.project.team11_tabling.domain.shop;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ShopRequestDto {
     private Long shopId;
     private String placeName;
     private String addressName;
     private String phone;
}
