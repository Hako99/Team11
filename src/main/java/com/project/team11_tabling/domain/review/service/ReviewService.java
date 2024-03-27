package com.project.team11_tabling.domain.review.service;

import com.project.team11_tabling.domain.review.dto.request.ReviewCreateRequestDto;
import com.project.team11_tabling.domain.review.dto.request.ReviewUpdateRequestDto;
import com.project.team11_tabling.domain.review.dto.response.GetReviewResponseDto;
import java.util.List;

public interface ReviewService {

  void createReview(ReviewCreateRequestDto reviewCreateRequestDto);

  void updateReview(ReviewUpdateRequestDto reviewUpdateRequestDto);

  GetReviewResponseDto getReview(Long reviewId);

 List<GetReviewResponseDto> getMyReviews(Long userId);

  void deleteReview(Long reviewId, Long userId);

}
