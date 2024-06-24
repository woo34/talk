package com.talk.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // Logger 변수 선언
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private RedisTemplate<String, Object> redisTemplate;
    private ChannelTopic topic;
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setTopic(ChannelTopic topic) {
        this.topic = topic;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 소켓 연결 확인
        logger.info("{} 연결됨", session.getId());
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        String jsonMessage = objectMapper.writeValueAsString(chatMessage);
        logger.info("jsonMessage {}", jsonMessage);
        // 로그 추가
        logger.info("Publishing message to Redis topic: {}", topic.getTopic());
        redisTemplate.convertAndSend(topic.getTopic(), jsonMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void broadcastMessage(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
