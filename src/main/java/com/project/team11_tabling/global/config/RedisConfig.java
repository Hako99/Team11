package com.project.team11_tabling.global.config;

import com.project.team11_tabling.domain.shop.service.redisMessage.RealtimeWaitingDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {
  @Bean
  RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    return container;
  }

}
