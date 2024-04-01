package com.project.team11_tabling.domain.shop;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ShopRequestDto {
     private Long id;
     private String place_name;
     private String address_name;
     private String phone;
}
