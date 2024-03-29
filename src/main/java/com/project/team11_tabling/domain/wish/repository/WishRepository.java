package com.project.team11_tabling.domain.wish.repository;

import com.project.team11_tabling.domain.wish.entity.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long>, WishRepositoryCustom{
  Optional<Wish> findWishByShopIdAndUserId(Long shopId, Long userId);
}
