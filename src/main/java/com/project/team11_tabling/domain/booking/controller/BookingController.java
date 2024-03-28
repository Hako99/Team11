package com.project.team11_tabling.domain.booking.controller;

import com.project.team11_tabling.domain.booking.service.BookingService;
import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  public ResponseEntity<BookingResponse> booking(
      @RequestBody @Valid BookingRequest request
  ) {

    BookingResponse response = bookingService.booking(request);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
