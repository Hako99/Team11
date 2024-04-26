package com.project.team11_tabling.domain.review.entity;


import com.project.team11_tabling.domain.review.dto.request.ReviewCreateRequestDto;
import com.project.team11_tabling.domain.review.dto.request.ReviewUpdateRequestDto;
import com.project.team11_tabling.global.util.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_id", nullable = false)
  private Long id;
  @Column(name = "shop_id", nullable = false)
  private Long shopId;
  @Column(name = "user_id", nullable = false)
  private Long userId;
  @Column(name = "description", nullable = false)
  private String description;


  public Review(ReviewCreateRequestDto reviewCreateRequestDto, Long userId) {
    this.shopId = reviewCreateRequestDto.getShopId();
    this.description = reviewCreateRequestDto.getDescription();
    this.userId = userId;
  }

  public void updateReview(ReviewUpdateRequestDto reviewUpdateRequestDto) {
    this.description = reviewUpdateRequestDto.getDescription();
  }
}
