package com.talk.service;

import com.talk.model.ChatRoom;
import com.talk.model.Messages;
import com.talk.repository.ChatRoomRepository;
import com.talk.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Messages> getAllMessages(){
        return messageRepository.findAll();
    }
}
