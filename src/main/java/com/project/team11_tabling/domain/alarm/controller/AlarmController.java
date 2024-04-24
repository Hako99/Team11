package com.project.team11_tabling.domain.alarm.controller;

import com.project.team11_tabling.domain.alarm.service.AlarmService;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import com.project.team11_tabling.global.jwt.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RequestMapping("/api/alarm")
@Controller
public class AlarmController {

  private final AlarmService alarmService;

  @GetMapping("/shop")
  public SseEmitter userAlarm(
      @RequestParam BookingType bookingType,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    return alarmService.subscribe(userDetails.getUserId(), bookingType);
  }

}
