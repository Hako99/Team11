package com.project.team11_tabling.domain.user.entity;

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
@NoArgsConstructor
@Table(name = "users")

public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String username;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private String phoneNumber;
  @Column(nullable = false)
  private boolean active = true;
  @Column(nullable = false)
  private String userInfo = "자기소개 해주세요";
  @Column(nullable = false)
  private String grade = "Bronze";

  public User(String email, String username, String password, String phoneNumber) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
  }

  public void updateGrade(String grade) {
    this.grade = grade;
  }

  public void inActiveUser() {
    this.active = false;
  }
}

