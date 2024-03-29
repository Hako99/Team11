package com.project.team11_tabling.domain.wish.service;

import com.project.team11_tabling.domain.user.entity.User;
import com.project.team11_tabling.domain.wish.dto.response.MyWishDto;
import java.util.List;

public interface WishService {

  void createWish(Long shopId, User user);

  List<MyWishDto> getMyWishs(Long userId);

  void deleteWish(Long shopId, User user);
}
