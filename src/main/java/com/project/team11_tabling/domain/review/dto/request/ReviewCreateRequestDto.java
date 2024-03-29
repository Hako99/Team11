package com.project.team11_tabling.domain.review.dto.request;


import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {

  private Long shopId;
  private Long bookingId;
  private String description;
  private Long star;

}
