package com.talk.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talk.repository.ChatRepository;

@Service
public class ChatService {
	
	@Autowired
	private ChatRepository dao;
	
	public List<Chat> list_chat(HashMap<String,Object> parameters)throws Exception {
		List<Chat> list = new ArrayList<Chat>();
		try { 
			list = dao.list_chat(parameters);

		} catch(Exception e) {
	        throw e;
	    }
		return list;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void insert_chat(Chat chat) throws Exception {
		try 
		{
			if(chat == null){
				throw new Exception("채팅방 추가 에러 발생입니다.");  
			}
			
			dao.insert_chat(chat);

		} catch(Exception e) {
			throw e;
		}
	}
}
