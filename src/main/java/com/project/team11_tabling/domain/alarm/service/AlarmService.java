package com.project.team11_tabling.domain.alarm.service;

import com.project.team11_tabling.domain.user.entity.User;
import com.project.team11_tabling.global.event.AlarmFinalEventDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {

  SseEmitter subscribe(Long id);
  SseEmitter subscribeDone(Long id);
  SseEmitter createEmitter(Long userId);
  SseEmitter createEmitterDone(Long userId);
  void sendToClient(Long userId, Object data);
  void sendMessageAndClose (AlarmFinalEventDto alarmFinalEventDto);

  User findUser (Long userId);

}
