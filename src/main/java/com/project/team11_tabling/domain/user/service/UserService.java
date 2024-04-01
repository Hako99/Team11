package com.project.team11_tabling.domain.user.service;

import com.project.team11_tabling.domain.user.dto.LoginRequestDto;
import com.project.team11_tabling.domain.user.dto.SignupRequestDto;
import com.project.team11_tabling.domain.user.entity.User;
import com.project.team11_tabling.domain.user.repository.UserRepository;
import com.project.team11_tabling.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  @Transactional
  public void signup(SignupRequestDto request) {
    String username = request.getUsername();
    String email = request.getEmail();
    String password = request.getPassword();
    String phoneNumber = request.getPhoneNumber();

    if (userRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("중복된 username 입니다.");
    }

    if (userRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("중복된 email 입니다.");
    }

    User user = new User(email, username, password, phoneNumber);
    userRepository.save(user);
  }

  public void login(LoginRequestDto request, HttpServletResponse res) {
    String username = request.getUsername();
    String password = request.getPassword();

    // 사용자 확인
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
    );

    if (!user.isActive()) {
      throw new IllegalArgumentException("비활성화된 유저입니다.");
    }

    // JWT 생성 및 헤더에 저장 후 Response 객체에 추가
    String token = jwtUtil.createToken(user.getUsername());
    jwtUtil.addJwtToHeader(token, res);
  }

  @Transactional
  public void inActiveUser(User user) {
    User findUser = userRepository.findById(user.getUserId()).orElseThrow(
        () -> new NoSuchElementException("존재하지 않는 회원입니다."));

    findUser.inActiveUser();
  }

  public List<User> findAllByEmailIn(List<String> emailList) {
    return userRepository.findAllByEmailIn(emailList);
  }
}
