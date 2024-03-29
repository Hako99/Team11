package com.project.team11_tabling.domain.wish.repository;

import com.project.team11_tabling.domain.wish.entity.QWish;
import com.project.team11_tabling.domain.wish.entity.Wish;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WishRepositoryImpl implements WishRepositoryCustom{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Wish> getWishs(Long userId) {
    QWish wish = QWish.wish;

    return jpaQueryFactory.select(wish)
        .from(wish)
        .where(wish.userId.eq(userId))
        .fetch();
  }
}
