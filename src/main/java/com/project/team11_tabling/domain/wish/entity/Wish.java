package com.project.team11_tabling.domain.wish.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "wish")
@NoArgsConstructor
public class Wish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wish_id", nullable = false)
  private Long wishId;

  @Column(name = "shop_id", nullable = false)
  private Long shopId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "active", nullable = false)
  private boolean active = true;

  public Wish(Long shopId, Long userId) {
    this.shopId = shopId;
    this.userId = userId;
  }

  public void createUpdate() {
    this.active = true;
  }

  public void deleteUpdate() {
    this.active = false;
  }
}
