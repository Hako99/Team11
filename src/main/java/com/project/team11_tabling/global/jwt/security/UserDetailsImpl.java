//package com.project.team11_tabling.global.jwt.security;
//
//import com.project.team11_tabling.domain.user.entity.User;
//import java.util.ArrayList;
//import java.util.Collection;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class UserDetailsImpl implements UserDetails {
//  private final User user;
//
//  Collection<GrantedAuthority> authorities = new ArrayList<>();
//
//  public UserDetailsImpl(User user) {
//    this.user = user;
//  }
//
//  public User getUser() {
//    return user;
//  }
//
//
//  public Long getUserId() {
//    return user.getUserId();
//  }
//  @Override
//  public String getPassword() {
//    return user.getPassword();
//  }
//
//  @Override
//  public String getUsername() {
//    return user.getUsername();
//  }
//
//  @Override
//  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return authorities;
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//    return true;
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return true;
//  }
//}
