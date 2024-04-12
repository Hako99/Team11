package com.project.team11_tabling.domain.review.repository;


import static com.project.team11_tabling.domain.review.entity.QReview.review;

import com.project.team11_tabling.domain.review.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;


  @Override
  public List<Review> getMyReviews(Long userId) {
    return jpaQueryFactory
        .select(review)
        .from(review)
        .where(review.userId.eq(userId))
        .orderBy(review.createdAt.asc())
        .fetch();
  }

  @Override
  public List<Review> getShopReviews(Long shopId) {
    return jpaQueryFactory
        .select(review)
        .from(review)
        .where(review.shopId.eq(shopId))
        .orderBy(review.createdAt.asc())
        .fetch();
  }


}
