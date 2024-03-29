package com.project.team11_tabling.domain.shop;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
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
  private String reviewCount;

  @Column(nullable = false)
  private Date openTime;

  @Column(nullable = false)
  private Date closeTime;

  public Shop(ShopRequestDto requestDto) {
    this.shopId = requestDto.getShopId();
    this.address = requestDto.getAddressName();
    this.city = requestDto.getAddressName().substring(0, 2);
    this.phone = requestDto.getPhone();
  }

  public void setTime(Date[] date) {
    this.openTime = date[0];
    this.closeTime = date[1];
  }
}
