package com.project.team11_tabling.domain.wish.repository;

import com.project.team11_tabling.domain.wish.entity.Wish;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepositoryCustom {
  List<Wish> getWishs(Long userId);
}
