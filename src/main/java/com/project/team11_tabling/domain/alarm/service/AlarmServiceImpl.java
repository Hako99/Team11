package com.project.team11_tabling.domain.alarm.service;

import com.project.team11_tabling.domain.alarm.repository.AlarmSseEmitterRepository;
import com.project.team11_tabling.domain.booking.entity.BookingType;
import com.project.team11_tabling.domain.user.entity.User;
import com.project.team11_tabling.domain.user.repository.UserRepository;
import com.project.team11_tabling.global.event.AlarmEvent;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AlarmServiceImpl")
@Transactional
public class AlarmServiceImpl implements AlarmService {

  private final AlarmSseEmitterRepository alarmSseEmitterRepository;
  private final UserRepository userRepository;
  private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;
  private static final String WAITING_MESSAGE = " 손님의 줄서기가 시작되었습니다.";
  private static final String DONE_MESSAGE = " 손님이 입장완료 되었습니다.";
  private static final String NOSHOW_MESSAGE = " 손님 입장 시간이 초과하여 입장 취소되었습니다.";
  private static final String CANCEL_MESSAGE = " 손님 줄서기를 취소하셨습니다.";


  @Override
  public SseEmitter subscribe(Long userId) {
    return createEmitter(userId);
  }

  @Async
  @TransactionalEventListener
  @Override
  public void sendMessage(AlarmEvent alarmEvent) {
    Long userId = alarmEvent.getBooking().getUserId();
    BookingType bookingType = alarmEvent.getBooking().getState();

    log.info("sendMessage:: shopId = {}, userId = {}, messageType = {}",
        alarmEvent.getBooking().getShopId(), userId, bookingType);

    User user = findUser(userId);
    SseEmitter emitter = alarmSseEmitterRepository.get(user.getUserId());
    String message = messageByBookingType(bookingType);

    if (bookingType.equals(BookingType.WAITING)) {
      try {
        emitter.send(SseEmitter.event()
            .name(user.getUsername() + " 손님")
            .data(user.getUsername() + message));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      try {
        emitter.send(SseEmitter.event()
            .name(user.getUsername() + " 손님")
            .data(user.getUsername() + message));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
        emitter.complete();
    }
  }

  private SseEmitter createEmitter(Long userId) {
    SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
    alarmSseEmitterRepository.save(userId, emitter);

    emitter.onCompletion(() -> alarmSseEmitterRepository.deleteById(userId));
    emitter.onTimeout(() -> alarmSseEmitterRepository.deleteById(userId));
    return emitter;
  }

  private User findUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));
  }

  private String messageByBookingType(BookingType bookingType) {
    return switch (bookingType) {
      case WAITING -> WAITING_MESSAGE;
      case DONE -> DONE_MESSAGE;
      case CANCEL -> CANCEL_MESSAGE;
      case NOSHOW -> NOSHOW_MESSAGE;
    };
  }
}
