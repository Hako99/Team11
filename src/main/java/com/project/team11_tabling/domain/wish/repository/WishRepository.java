package com.project.team11_tabling.domain.wish.repository;

import com.project.team11_tabling.domain.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

}
