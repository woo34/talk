package com.talk.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talk.service.ChatService;

@RestController
//@Controller
public class ChatController {

	@Autowired
	ChatService chatService;
	
    @GetMapping("/")
    public String chat() {
        return "index";
    }
    
	//채팅방
	@RequestMapping("/list_chat")
	public List<Chat> list_chat(
			@RequestParam("chat_no") String chat_no
			) throws Exception {
		HashMap<String, Object> parameters = new HashMap<String, Object>();		
		
		parameters.put("P_CHAT_NO", chat_no);

		List<Chat> chat = chatService.list_chat(parameters);

		return chat;
	}
}
