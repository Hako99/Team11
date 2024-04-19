package com.project.team11_tabling.domain.booking.controller;

import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.domain.booking.service.BookingService;
import com.project.team11_tabling.global.jwt.security.UserDetailsImpl;
import com.project.team11_tabling.global.response.CommonResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  public ResponseEntity<CommonResponse<BookingResponse>> booking(
      @RequestBody @Valid BookingRequest request,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    BookingResponse response = bookingService.booking(request, userDetails);

    return CommonResponse.ok(response);
  }

  @DeleteMapping("/{bookingId}")
  public ResponseEntity<CommonResponse<BookingResponse>> cancelBooking(
      @PathVariable Long bookingId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    BookingResponse response = bookingService.cancelBooking(bookingId, userDetails);

    return CommonResponse.ok(response);
  }

  @GetMapping("/my")
  public ResponseEntity<CommonResponse<List<BookingResponse>>> getMyBookings(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    List<BookingResponse> responses = bookingService.getMyBookings(userDetails);

    return CommonResponse.ok(responses);
  }

  @PutMapping("/{bookingId}")
  public ResponseEntity<CommonResponse<BookingResponse>> noShow(
      @PathVariable Long bookingId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    BookingResponse response = bookingService.noShow(bookingId, userDetails);

    return CommonResponse.ok(response);
  }

  @GetMapping("/shop/{shopId}")
  public ResponseEntity<CommonResponse<BookingResponse>> getShopBooking(
      @PathVariable Long shopId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    BookingResponse response = bookingService.getShopBooking(shopId, userDetails);

    return CommonResponse.ok(response);
  }

}
