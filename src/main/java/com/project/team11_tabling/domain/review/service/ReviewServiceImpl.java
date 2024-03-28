package com.project.team11_tabling.domain.review.service;

import com.project.team11_tabling.domain.booking.Entity.Booking;
import com.project.team11_tabling.domain.booking.repository.BookingRepository;
import com.project.team11_tabling.domain.review.dto.request.ReviewCreateRequestDto;
import com.project.team11_tabling.domain.review.dto.request.ReviewUpdateRequestDto;
import com.project.team11_tabling.domain.review.dto.response.GetReviewResponseDto;
import com.project.team11_tabling.domain.review.entity.Review;
import com.project.team11_tabling.domain.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final BookingRepository bookingRepository;

  // 리뷰 생성
  @Override
  public void createReview(Long userId, ReviewCreateRequestDto reviewCreateRequestDto) {
    Booking booking = findBooking(reviewCreateRequestDto.getBookingId());
    if (booking.getUserId != userId) {
      throw new IllegalArgumentException("본인이 이용한 내역에만 리뷰를 남길 수 있습니다.");
    }
    if (booking.getState != DONE) {
      throw new IllegalArgumentException("아직 진행중인 사항에는 리뷰를 남길 수 없습니다.");
    }
    Review review = new Review(reviewCreateRequestDto, userId);
    reviewRepository.save(review);
  }

  public Booking findBooking(Long bookingId) {
    return bookingRepository.findById(bookingId)
        .orElseThrow(() -> new EntityNotFoundException("줄서기 정보가 없습니다."));
  }

  // 리뷰 수정
  @Override
  public void updateReview(Long userId, ReviewUpdateRequestDto reviewUpdateRequestDto) {
    Review review = reviewRepository.findById(reviewUpdateRequestDto.getReviewId())
        .orElseThrow(() -> new NullPointerException("수정할 리뷰가 없습니다."));
    if (!Objects.equals(userId, review.getUserId())) {
      throw new IllegalArgumentException("본인이 작성한 리뷰만 수정이 가능합니다.");
    }
    review.updateReview(reviewUpdateRequestDto);
  }

  // 리뷰 단일 조회
  @Override
  public GetReviewResponseDto getReview(Long reviewId) {
    Review review = findReview(reviewId);
    return new GetReviewResponseDto(review);
  }

  public Review findReview(Long reviewId) {
    return reviewRepository.findById(reviewId)
        .orElseThrow(() -> new NullPointerException("리뷰가 존재하지 않습니다."));
  }

  // 유저 리뷰 일괄 조회 (본인것만)
  @Override
  public List<GetReviewResponseDto> getMyReviews(Long userId) {
    return reviewRepository.getMyReviews(userId).stream().map(GetReviewResponseDto::new).toList();
  }

  // 리뷰 일괄 조회 (가게 기준)
  @Override
  public List<GetReviewResponseDto> getShopReviews(Long shopId) {
    return reviewRepository.getShopReviews(shopId).stream().map(GetReviewResponseDto::new).toList();
  }

  @Override
  public void deleteReview(Long reviewId, Long userId) {
    Review review = findReview(reviewId);
    if (!Objects.equals(userId, review.getUserId())) {
      throw new IllegalArgumentException("리뷰를 삭제할 수 있는 권한이 없습니다.");
    }
    reviewRepository.delete(review);
  }
}
