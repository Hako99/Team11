package com.project.team11_tabling.domain.shop.repository;

import com.project.team11_tabling.domain.shop.entity.Shop;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

  @Override
  Optional<Shop> findById(Long aLong);
  Optional<Shop> findByShopId(Long shopId);

  List<Shop> findByPopularShopOrderByReviewCountDesc(boolean isPopularShop);
}
