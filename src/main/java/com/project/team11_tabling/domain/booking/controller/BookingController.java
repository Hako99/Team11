package com.project.team11_tabling.domain.booking.controller;

import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import com.project.team11_tabling.domain.booking.service.BookingService;
import com.project.team11_tabling.global.response.CommonResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  public ResponseEntity<CommonResponse<BookingResponse>> booking(
      @RequestBody @Valid BookingRequest request
  ) {

    BookingResponse response = bookingService.booking(request);

    return CommonResponse.ok(response);
  }

  @DeleteMapping("/{bookingId}")
  public ResponseEntity<CommonResponse<BookingResponse>> deleteBooking(
      @PathVariable Long bookingId
  ) {

    // TODO: auth
    BookingResponse response = bookingService.cancelBooking(bookingId);

    return CommonResponse.ok(response);
  }

  @GetMapping("/my")
  public ResponseEntity<CommonResponse<List<BookingResponse>>> getMyBookings(
      // TODO: auth
  ) {

    List<BookingResponse> responses = bookingService.getMyBookings();

    return CommonResponse.ok(responses);
  }

  @PutMapping("/{bookingId}")
  public ResponseEntity<CommonResponse<BookingResponse>> completeBooking(
      @PathVariable Long bookingId,
      @RequestParam BookingType type
  ) {

    BookingResponse response = bookingService.completeBooking(bookingId, type);

    return CommonResponse.ok(response);
  }

}
