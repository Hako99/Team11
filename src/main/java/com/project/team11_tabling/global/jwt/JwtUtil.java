package com.project.team11_tabling.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtil {

  // Header KEY 값
  public static final String AUTHORIZATION_HEADER = "Authorization";

  // Token 식별자
  public static final String BEARER_PREFIX = "Bearer ";

  // 토큰 만료시간
  private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

  @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
  private String secretKey;
  private Key key;
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  // 로그 설정
  public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  // 토큰 생성
  public String createToken(String username) {
    Date date = new Date();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(username) // 사용자 식별자값(ID)
            .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
            .setIssuedAt(date) // 발급일
            .signWith(key, signatureAlgorithm) // 암호화 알고리즘
            .compact();
  }

  // JWT Header 에 저장
  public void addJwtToHeader(String token, HttpServletResponse res) {
    try {
      token = URLEncoder.encode(token, "utf-8")
          .replaceAll("\\+", " "); // Cookie Value 에는 공백이 불가능해서 encoding 진행

//			Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
//			cookie.setPath("/");

      res.addHeader(AUTHORIZATION_HEADER, token);
//			// Response 객체에 Cookie 추가
//			res.addCookie(cookie);
    } catch (UnsupportedEncodingException e) {
      logger.error(e.getMessage());
    }
  }

  // header 에서 JWT 가져오기
  public String getJwtFromHeader(HttpServletRequest request) {

    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

//		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//			return bearerToken.substring(7);
//		}

    return bearerToken;
  }

  // JWT 토큰 substring
  public String substringToken(String tokenValue) {
    if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
      return tokenValue.substring(7);
    }
    logger.error("Not Found Token");
    throw new NullPointerException("Not Found Token");
  }

  // 토큰 검증
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException | SignatureException e) {
      logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      logger.error("Expired JWT token, 만료된 JWT token 입니다.");
    } catch (UnsupportedJwtException e) {
      logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }
    return false;
  }

  // 토큰에서 사용자 정보 가져오기
  public Claims getUserInfoFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }
}