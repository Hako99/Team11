package com.project.team11_tabling.domain.review.repository;

import com.project.team11_tabling.domain.review.entity.Review;
import java.util.List;

public interface ReviewRepositoryCustom {

  List<Review> getMyReviews(Long userId);

  List<Review> getShopReviews(Long shopId);

}
