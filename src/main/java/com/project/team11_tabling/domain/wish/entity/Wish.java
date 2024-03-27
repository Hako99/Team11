package com.project.team11_tabling.domain.wish.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "wish")
public class Wish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wish_id", nullable = false)
  private Long wishId;

//  @ManyToOne
//  @JoinColumn(name = "user_id")
//  private Shop shop;

//  @ManyToOne
//  @JoinColumn(name = "user_id")
//  private User user;
}
