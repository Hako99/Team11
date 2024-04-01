//package com.project.team11_tabling.domain.review.controller;
//
//
//import com.project.team11_tabling.domain.review.dto.request.ReviewCreateRequestDto;
//import com.project.team11_tabling.domain.review.dto.request.ReviewUpdateRequestDto;
//import com.project.team11_tabling.domain.review.dto.response.GetReviewResponseDto;
//import com.project.team11_tabling.domain.review.service.ReviewService;
//import com.project.team11_tabling.global.jwt.security.UserDetailsImpl;
//import com.project.team11_tabling.global.response.CommonResponse;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/reviews")
//public class ReviewController {
//
//  private final ReviewService reviewService;
//
//  // 리뷰 생성
//  @PostMapping
//  public void createReviews(@AuthenticationPrincipal UserDetailsImpl userDetails,
//      @RequestBody ReviewCreateRequestDto reviewCreateRequestDto) {
//    reviewService.createReview(userDetails.getUserId(), reviewCreateRequestDto);
//  }
//
//  // 리뷰 수정
//  @PutMapping
//  public void updateReviews(@AuthenticationPrincipal UserDetailsImpl userDetails,
//      @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto) {
//    reviewService.updateReview(userDetails.getUserId(), reviewUpdateRequestDto);
//  }
//
//  // 리뷰 삭제
//  @DeleteMapping("/{reviewId}")
//  public void deleteReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
//      @PathVariable Long reviewId) {
//    reviewService.deleteReview(userDetails.getUserId(), reviewId);
//  }
//
//  // 리뷰 조회 단일
//  @GetMapping("/{reviewId}")
//  public ResponseEntity<CommonResponse<GetReviewResponseDto>> getReview(
//      @PathVariable Long reviewId) {
//    return CommonResponse.ok(reviewService.getReview(reviewId));
//  }
//
//  // 유저의 리뷰 조회 전체
//  @GetMapping
//  public ResponseEntity<CommonResponse<List<GetReviewResponseDto>>> getReviews(
//      @AuthenticationPrincipal UserDetailsImpl userDetails) {
//    return CommonResponse.ok(reviewService.getMyReviews(userDetails.getUserId()));
//  }
//
//  // 상점의 리뷰 조회 전체
//  @GetMapping("/{shopId}")
//  public ResponseEntity<CommonResponse<List<GetReviewResponseDto>>> getReviews(
//      @PathVariable Long shopId) {
//    return CommonResponse.ok(reviewService.getShopReviews(shopId));
//  }
//}
