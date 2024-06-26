package com.talk.service;

import com.talk.model.ChatRoom;
import com.talk.repository.ChatRoomRepository;
import com.talk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {
    private final ChatRoomRepository chatroomRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatroomRepository) {
        this.chatroomRepository = chatroomRepository;
    }

    public List<ChatRoom> getAllChatRooms(){
        return chatroomRepository.findAll();
    }
}
