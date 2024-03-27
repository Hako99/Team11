package com.project.team11_tabling.user.repository;


import com.project.team11_tabling.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
