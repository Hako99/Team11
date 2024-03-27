package com.project.team11_tabling.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewResponseDto {

  private Long userId;
  private Long shopId;
  private Long bookingId;
  private String description;
  private Long star;

}
