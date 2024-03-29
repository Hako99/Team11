package com.project.team11_tabling.domain.review.repository;

import com.project.team11_tabling.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

}
