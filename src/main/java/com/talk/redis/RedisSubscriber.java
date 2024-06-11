package com.talk.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.talk.handler.ChatWebSocketHandler;

@Component
public class RedisSubscriber implements MessageListener {

    private ChatWebSocketHandler chatWebSocketHandler;

    @Autowired
    public void setChatWebSocketHandler(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        chatWebSocketHandler.broadcastMessage(new String(message.getBody()));
    }
}
