package com.talk.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talk.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.talk.handler.ChatWebSocketHandler;

@Component
public class RedisSubscriber implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(RedisSubscriber.class);
    private ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public void setChatWebSocketHandler(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        logger.info("Received message from Redis: {}", msg);
        try {
            ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);
            String jsonMessage = objectMapper.writeValueAsString(chatMessage);
            chatWebSocketHandler.broadcastMessage(jsonMessage);
        } catch (Exception e) {
            logger.error("Failed to parse message from Redis", e);
        }
    }
}
