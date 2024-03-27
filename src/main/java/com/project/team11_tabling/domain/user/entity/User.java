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
  private Long UserId;

  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String username;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private Long phoneNumber;

  public User(String email, String username, String password, Long phoneNumber) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
  }

  public void UpdateUsername(String Username) {
    this.username = username;
  }

  public void UpdatePassword(String changePassword) {
    this.password = changePassword;
  }

}
