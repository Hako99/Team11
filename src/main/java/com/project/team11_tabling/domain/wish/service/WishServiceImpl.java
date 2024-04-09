package com.project.team11_tabling.domain.wish.service;

import com.project.team11_tabling.domain.shop.entity.Shop;
import com.project.team11_tabling.domain.shop.repository.ShopRepository;
import com.project.team11_tabling.domain.user.entity.User;
import com.project.team11_tabling.domain.wish.dto.response.MyWishDto;
import com.project.team11_tabling.domain.wish.entity.Wish;
import com.project.team11_tabling.domain.wish.repository.WishRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishServiceImpl implements WishService {

  private final WishRepository wishRepository;
  private final ShopRepository shopRepository;

  @Override
  public void createWish(Long shopId, User user) {

    Shop shop = shopRepository.findById(shopId)
        .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다"));
    Wish wish = wishRepository.findWishByShopIdAndUserId(
        shop.getId(), user.getUserId()).orElse(null);

    if (wish == null) {
      wishRepository.save(new Wish(shop.getId(), user.getUserId()));
    } else {
      wish.createUpdate();
    }
  }

  @Override
  public List<MyWishDto> getMyWishs(Long userId) {

    return wishRepository.getWishs(userId).stream().map(m -> MyWishDto.builder()
        .userId(m.getUserId()).shopId(m.getShopId()).build()).collect(
        Collectors.toList());
  }

  @Override
  public void deleteWish(Long shopId, User user) {

    Wish wish = wishRepository.findWishByShopIdAndUserId(shopId, user.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("관심 매장을 찾을 수 없습니다"));

    wish.deleteUpdate();
  }
}
