package com.project.team11_tabling.domain.booking.controller;

import com.project.team11_tabling.domain.booking.dto.BookingRequest;
import com.project.team11_tabling.domain.booking.dto.BookingResponse;
import com.project.team11_tabling.domain.booking.service.BookingService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<BookingResponse> booking(
      @RequestBody @Valid BookingRequest request
  ) {

    BookingResponse response = bookingService.booking(request);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{bookingId}")
  public ResponseEntity<BookingResponse> deleteBooking(
      @PathVariable Long bookingId
  ) {

    // TODO: auth
    BookingResponse response = bookingService.cancelBooking(bookingId);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/my")
  public ResponseEntity<List<BookingResponse>> getMyBookings(
      // TODO: auth, shopsBookings
  ) {

    List<BookingResponse> responses = bookingService.getMyBookings();

    return new ResponseEntity<>(responses, HttpStatus.OK);
  }

  @PutMapping("/{bookingId}")
  public ResponseEntity<BookingResponse> completeBooking(
      @PathVariable Long bookingId
  ) {
    BookingResponse response = bookingService.completeBooking(bookingId);
    // TODO : done, noShow state

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
