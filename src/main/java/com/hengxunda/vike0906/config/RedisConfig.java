package com.hengxunda.vike0906.config;

/**
 * Created by linmingren on 2017/9/21.
 */

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {
    @Bean
    RedisMessageListenerContainer container(@Qualifier("redisConnectionFactory") RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    MessageListenerAdapter tradeListener() {
        return new MessageListenerAdapter(new DummyListener(), "dummy");
    }

    public static class DummyListener {
        public void dummy(String msg) {

        }
    }
}
