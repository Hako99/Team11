package com.project.team11_tabling.domain.shop;


import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;

@Entity
@Getter
@Table(name = "shop")
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String reviewCount;

  @Column(nullable = false)
  private Date openTime;

  @Column(nullable = false)
  private Date closeTime;

}
