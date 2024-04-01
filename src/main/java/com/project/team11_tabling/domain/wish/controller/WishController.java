//package com.project.team11_tabling.domain.wish.controller;
//
//import com.project.team11_tabling.domain.wish.dto.response.MyWishDto;
//import com.project.team11_tabling.domain.wish.dto.response.WishResponse;
//import com.project.team11_tabling.domain.wish.service.WishService;
//import com.project.team11_tabling.global.jwt.security.UserDetailsImpl;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/wishs")
//public class WishController {
//
//  private final WishService wishService;
//
//  @PostMapping("/{shopId}")
//  public ResponseEntity<WishResponse> createWish(@PathVariable Long shopId,
//      @AuthenticationPrincipal UserDetailsImpl userDetails) {
//    wishService.createWish(shopId, userDetails.getUser());
//    return ResponseEntity.ok().body(WishResponse.success(200, "관심 매장으로 등록되었습니다"));
//  }
//
//  @GetMapping("/{userId}")
//  public ResponseEntity<WishResponse> getMyWishs(
//      @AuthenticationPrincipal UserDetailsImpl userDetails) {
//    List<MyWishDto> myWish = wishService.getMyWishs(userDetails.getUser().getUserId());
//    return ResponseEntity.ok().body(WishResponse.success(200, myWish));
//  }
//
//  @DeleteMapping("/{shopId}")
//  public ResponseEntity<WishResponse> deleteWish(@PathVariable Long shopId,
//      @AuthenticationPrincipal UserDetailsImpl userDetails) {
//    wishService.deleteWish(shopId, userDetails.getUser());
//    return ResponseEntity.ok().body(WishResponse.success(200, "매장이 삭제되었습니다"));
//  }
//}
