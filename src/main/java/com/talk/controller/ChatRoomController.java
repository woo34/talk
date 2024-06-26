package com.talk.controller;

import com.talk.model.ChatRoom;
import com.talk.service.ChatRoomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatroomService;

    public ChatRoomController(ChatRoomService chatroomService){
        this.chatroomService = chatroomService;
    }

    public List<ChatRoom> getAllChatRooms(){
        return chatroomService.getAllChatRooms();
    }

}
