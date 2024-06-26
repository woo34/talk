package com.talk.controller;

import com.talk.model.Messages;
import com.talk.model.Users;
import com.talk.service.MessageService;
import com.talk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Messages> getAllMessages() {
        return messageService.getAllMessages();
    }
}
