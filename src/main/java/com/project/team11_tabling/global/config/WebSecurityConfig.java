//package com.project.team11_tabling.global.config;
//
//import com.project.team11_tabling.global.jwt.JwtUtil;
//import com.project.team11_tabling.global.jwt.security.JwtAuthorizationFilter;
//import com.project.team11_tabling.global.jwt.security.UserDetailsServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity // Spring Security 지원을 가능하게 함
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//  private final JwtUtil jwtUtil;
//  private final UserDetailsServiceImpl userDetailsService;
//  @Bean
//  public JwtAuthorizationFilter jwtAuthorizationFilter() {
//    return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
//  }
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    // CSRF 설정
//    http.csrf((csrf) -> csrf.disable());
//
////    // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
//    http.sessionManagement((sessionManagement) ->
//        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//    );
//
//    http.authorizeHttpRequests((authorizeHttpRequests) ->
//            authorizeHttpRequests
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                .permitAll() // resources 접근 허용 설정
//            .requestMatchers("/api/users/signup").permitAll()
//            .requestMatchers("/api/users/login").permitAll()// '/api/user/'로 시작하는 요청 모두 접근 허가
//            .anyRequest().authenticated() // 그 외 모든 요청 인증처리
//    );
//
//    // 필터 관리
//    http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    return http.build();
//  }
//}
