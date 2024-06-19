package com.talk.repository;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.talk.dao.ChatDao;

@Repository
public class ChatRepository {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private ChatDao chatdao;
	
	public int insert_chat(Chat parameters) {
		return sqlSession.insert(this.getClass().getName() + ".insert_chat", parameters);
	}
	
	public List<Chat> list_chat(HashMap<String, Object> parameters) throws Exception{
		return sqlSession.selectList(this.getClass().getName() + ".list_chat", parameters);
	}
}
