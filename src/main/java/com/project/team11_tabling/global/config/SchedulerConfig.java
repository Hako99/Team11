package com.project.team11_tabling.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    // Thread Pool 설정
    ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();

    // Thread 개수 설정
    threadPool.setPoolSize(4);
    threadPool.initialize();

    taskRegistrar.setTaskScheduler(threadPool);
  }
}
