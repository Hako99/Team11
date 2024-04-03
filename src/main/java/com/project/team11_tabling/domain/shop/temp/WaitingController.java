package com.project.team11_tabling.domain.shop.temp;

import com.project.team11_tabling.domain.shop.temp.kafka.kafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class WaitingController {

  private final WaitingQueue queue;
  private final kafkaProducer producer;

  @PostMapping
  public ResponseEntity<String> test() {

    queue.addQueue("3", "2");
    Long size = queue.queueSize("1");
    return ResponseEntity.status(HttpStatus.OK.value()).body("완료" + size);

  }

  @PostMapping("/k")
  public ResponseEntity<String> test_k() {

    producer.create();
    return ResponseEntity.status(HttpStatus.OK.value()).body("완료");

  }

  @PostMapping("/w")
  public ResponseEntity<String> registerWaiting() {
    queue.addQueue("1", "10");
    return ResponseEntity.status(HttpStatus.OK.value()).body("웨이팅 등록되었습니다.");

  }

  //조회
  @PostMapping("/c")
  public ResponseEntity<String> getWaitingCount() {
    Long size = queue.queueSize("1");
    if(size == 0){
      return ResponseEntity.status(HttpStatus.OK.value()).body("바로 입장가능합니다.");
    }
    else{
      System.out.println("예상대기시간 : " + size*15 + "초");
      return ResponseEntity.status(HttpStatus.OK.value()).body("현재 대기 수 : "+ size +" 명   --웨이팅 등록해주세요");
    }

  }

}
