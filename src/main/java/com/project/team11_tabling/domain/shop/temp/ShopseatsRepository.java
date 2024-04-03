package com.project.team11_tabling.domain.shop.temp;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopseatsRepository extends JpaRepository<Shopseats, Long> {

  Optional<Shopseats> findByShopId(Long shopId);
}
