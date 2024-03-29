package com.project.team11_tabling.domain.review.service;

import com.project.team11_tabling.domain.review.dto.request.ReviewCreateRequestDto;
import com.project.team11_tabling.domain.review.dto.request.ReviewUpdateRequestDto;
import com.project.team11_tabling.domain.review.dto.response.GetReviewResponseDto;
import java.util.List;

public interface ReviewService {

  void createReview(Long userId, ReviewCreateRequestDto reviewCreateRequestDto);

  void updateReview(Long userId, ReviewUpdateRequestDto reviewUpdateRequestDto);

  GetReviewResponseDto getReview(Long reviewId);

  List<GetReviewResponseDto> getMyReviews(Long userId);

  List<GetReviewResponseDto> getShopReviews(Long shopId);

  void deleteReview(Long reviewId, Long userId);

}
