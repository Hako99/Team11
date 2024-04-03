package com.project.team11_tabling.domain.shop.repository;

import com.project.team11_tabling.domain.shop.entity.ShopSeats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopSeatsRepository extends JpaRepository<ShopSeats, Long> {

  ShopSeats findByShopId(Long shopId);

}
