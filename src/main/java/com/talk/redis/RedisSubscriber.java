package com.talk.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talk.model.Messages;
import com.talk.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.talk.handler.ChatWebSocketHandler;

import java.util.Date;

@Component
public class RedisSubscriber implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(RedisSubscriber.class);
    private ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public void setChatWebSocketHandler(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        logger.info("Received message from Redis: {}", msg);
        try {
            Messages chatMessage = objectMapper.readValue(msg, Messages.class);
            String jsonMessage = objectMapper.writeValueAsString(chatMessage);
            chatWebSocketHandler.broadcastMessage(jsonMessage);

//            saveMessageToDatabase(jsonMessage);
        } catch (Exception e) {
            logger.error("Failed to parse message from Redis", e);
        }
    }

    private void saveMessageToDatabase(String messageContent) {
        // 메시지 엔터티 생성 및 저장
        Messages messages = new Messages();
        messages.setContext(messageContent);
        messages.setInsertDts(new Date());
        messageRepository.save(messages);
    }
}
