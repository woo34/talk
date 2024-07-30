package com.talk.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talk.model.Messages;
import com.talk.redis.RedisSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
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
    private RedisMessageListenerContainer redisMessageListener;
    private RedisSubscriber redisSubscriber;

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setRedisMessageListenerContainer(RedisMessageListenerContainer redisMessageListener){
        this.redisMessageListener = redisMessageListener;
    }

    @Autowired
    public void setRedisSubscriber(RedisSubscriber redisSubscriber) {this.redisSubscriber = redisSubscriber;}

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

        Messages chatMessage = objectMapper.readValue(message.getPayload(), Messages.class);
        if(session.getId() != null && !"".equals(session.getId())){
            chatMessage.setSession(session.getId());
        }
        String jsonMessage = objectMapper.writeValueAsString(chatMessage);
        topic = topic.of(String.valueOf(chatMessage.getRoomId()));
        logger.info("Publishing message to Redis topic: {}", topic.getTopic());
        logger.info("jsonMessage {}", jsonMessage);

        redisMessageListener.addMessageListener(redisSubscriber, topic);
        //publish
        redisTemplate.convertAndSend(topic.getTopic(), jsonMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void broadcastMessage(Messages message) throws JsonProcessingException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen() && !message.getSession().equals(session.getId())) {
                String jsonMessage = objectMapper.writeValueAsString(message);
                try {
                    session.sendMessage(new TextMessage(jsonMessage));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
