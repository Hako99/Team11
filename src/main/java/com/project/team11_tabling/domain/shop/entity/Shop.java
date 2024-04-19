package com.project.team11_tabling.domain.shop.entity;


import com.project.team11_tabling.domain.shop.dto.ShopRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "shop")
@RequiredArgsConstructor
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long shopId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String phone;

  @Column
  private Integer reviewCount;

  @Column(nullable = false)
  private boolean popularShop = false;

  @Column(nullable = false)
  private LocalTime openTime;

  @Column(nullable = false)
  private LocalTime closeTime;

  public Shop(ShopRequestDto requestDto) {
    this.shopId = requestDto.getId();
    this.name = requestDto.getPlace_name();
    this.address = requestDto.getAddress_name();
    this.city = requestDto.getAddress_name().substring(0, 2);
    this.phone = requestDto.getPhone();
    this.reviewCount = 0;
  }

  public void updateTime(LocalTime[] time) {
    this.openTime = time[0];
    this.closeTime = time[1];
  }

  public void popularShopUpdate(boolean popularShop) {
    this.popularShop = popularShop;
  }
}
