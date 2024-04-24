package com.project.team11_tabling.global.scheduler;

import com.project.team11_tabling.domain.shop.entity.ShopSeats;
import com.project.team11_tabling.domain.shop.repository.ShopSeatsRepository;
import com.project.team11_tabling.global.batch.BookingBatch;
import com.project.team11_tabling.global.batch.PopularShopBatch;
import com.project.team11_tabling.global.batch.UserGradeBatch;
import com.project.team11_tabling.global.event.CallingEvent;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduler {

  private final ShopSeatsRepository shopSeatsRepository;
  private final ApplicationEventPublisher eventPublisher;
  private final JobLauncher jobLauncher;
  private final BookingBatch bookingBatch;
  private final UserGradeBatch userGradeBatch;
  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;
  private final DataSource dataSource;
  private final PopularShopBatch popularShopBatch;


  @Scheduled(fixedDelay = 30000)
  public void addAvailableSeat() {
    log.info("addAvailableSeat");

    List<ShopSeats> shopSeats = shopSeatsRepository.findAll();

    for (ShopSeats s : shopSeats) {
      if (s.getAvailableSeats() < s.getSeats()) {
        s.addAvailableSeat();
        shopSeatsRepository.save(s);
      }
    }

    eventPublisher.publishEvent(new CallingEvent());
  }


  @Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
  public void weeklyBooking() {
    Map<String, JobParameter<?>> confMap = new HashMap<>();

    LocalDateTime now = LocalDateTime.now();

    confMap.put("지난주 예약 내역", new JobParameter<>(now, LocalDateTime.class));
    JobParameters jobParameters = new JobParameters(confMap);

    try {
      jobLauncher.run(bookingBatch.bookingJob(jobRepository,transactionManager), jobParameters);
    } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
             | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

      log.error(e.getMessage());
    }
  }


  @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
  public void weeklyUpdateUser() {
    Map<String, JobParameter<?>> confMap = new HashMap<>();

    LocalDateTime now = LocalDateTime.now();

    confMap.put("주간 회원 등급 업데이트", new JobParameter<>(now, LocalDateTime.class));
    JobParameters jobParameters = new JobParameters(confMap);

    try {
      jobLauncher.run(userGradeBatch.userGradeJob(jobRepository,transactionManager, dataSource), jobParameters);
    } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
             | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

      log.error(e.getMessage());
    }
  }


  @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
  public void weeklyPopularShop() {
    Map<String, JobParameter<?>> confMap = new HashMap<>();

    LocalDateTime now = LocalDateTime.now();

    confMap.put("주간 인기매장 업데이트", new JobParameter<>(now, LocalDateTime.class));
    JobParameters jobParameters = new JobParameters(confMap);

    try {
      jobLauncher.run(popularShopBatch.popularShopJob(jobRepository,transactionManager, dataSource), jobParameters);
    } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
             | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

      log.error(e.getMessage());
    }
  }
}
