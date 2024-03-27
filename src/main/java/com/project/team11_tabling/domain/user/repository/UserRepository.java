package com.project.team11_tabling.domain.user.repository;


import com.project.team11_tabling.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
