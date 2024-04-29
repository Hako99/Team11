package com.project.team11_tabling.domain.shop.service.redisMessage;

import com.project.team11_tabling.domain.shop.service.redisMessage.RedisSubscriber;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j(topic = "topic repository")
public class ShopTopicRepository {
  private Map<String, ChannelTopic> topics = new HashMap<>();

  private final RedisMessageListenerContainer container;
  private final RedisSubscriber redisSubscriber;


  public void enterShopId(String roomId) {
    ChannelTopic topic;
    if (topics.get(roomId) == null) {
      log.info("topic 생성");
      topic = new ChannelTopic(roomId);
      topics.put(roomId, topic);
      initializeListeners(topic);
    }
  }

  public ChannelTopic getTopic(String shopId){
    return topics.get(shopId);
  }

  public void initializeListeners(ChannelTopic topic) {
    log.info("initialize");
    MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(redisSubscriber, "handleMessage");
    container.addMessageListener(listenerAdapter, topic);
  }
}
