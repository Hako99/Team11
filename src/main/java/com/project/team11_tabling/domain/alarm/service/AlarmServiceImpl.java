package com.project.team11_tabling.domain.alarm.service;

import com.project.team11_tabling.domain.alarm.repository.AlarmSseEmitterRepository;
import com.project.team11_tabling.domain.user.entity.User;
import com.project.team11_tabling.domain.user.repository.UserRepository;
import com.project.team11_tabling.global.event.AlarmFinalEventDto;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmServiceImpl implements AlarmService {

  private final AlarmSseEmitterRepository alarmSseEmitterRepository;
  private final UserRepository userRepository;
  private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;

  @Override
  public SseEmitter subscribe(Long userId) {
    SseEmitter emitter = createEmitter(userId);
    User user = findUser(userId);
    sendToClient(userId, user.getUsername() + " 손님의 줄서기가 시작되었습니다.");
    return emitter;
  }

  @Override
  public SseEmitter subscribeDone(Long userId) {
    return createEmitterDone(userId);
  }

  public SseEmitter createEmitter(Long userId) {
    SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
    alarmSseEmitterRepository.save(userId, emitter);

    emitter.onCompletion(() -> alarmSseEmitterRepository.deleteById(userId));
    emitter.onTimeout(() -> alarmSseEmitterRepository.deleteById(userId));

    return emitter;
  }

  public SseEmitter createEmitterDone(Long userId) {
    SseEmitter emitter = new SseEmitter(1000L);
    alarmSseEmitterRepository.save(userId, emitter);

    emitter.onCompletion(() -> alarmSseEmitterRepository.deleteById(userId));
    emitter.onTimeout(() -> alarmSseEmitterRepository.deleteById(userId));

    return emitter;
  }

  public void sendToClient(Long userId, Object data) {
    SseEmitter emitter = alarmSseEmitterRepository.get(userId);
    if (emitter != null) {
      try {
        emitter.send(SseEmitter.event()
            .id(String.valueOf(userId))
            .name("sse")
            .data(data)
            .comment("sse 접속 성공"));
      } catch (IOException e) {
        alarmSseEmitterRepository.deleteById(userId);
        emitter.completeWithError(e);
      }
    }
  }

  @EventListener
  public void sendMessageAndClose(AlarmFinalEventDto alarmFinalEventDto) {
    User user = findUser(alarmFinalEventDto.getUserId());
    SseEmitter emitter = alarmSseEmitterRepository.get(user.getUserId());
    try {
      emitter.send(SseEmitter.event()
          .name(user.getUsername() + " 손님")
          .data(alarmFinalEventDto.getMessage()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    emitter.complete();
  }

  public User findUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));
  }
}
