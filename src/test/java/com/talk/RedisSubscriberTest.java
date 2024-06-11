package com.talk;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.talk.redis.RedisSubscriber;

@SpringBootTest
public class RedisSubscriberTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testRedisSubscriberBeanExists() {
        // Check if the RedisSubscriber bean is present in the application context
        RedisSubscriber redisSubscriber = applicationContext.getBean(RedisSubscriber.class);
        assertThat(redisSubscriber).isNotNull();
    }
}
