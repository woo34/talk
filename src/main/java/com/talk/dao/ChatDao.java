package com.talk.dao;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDao {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert_chat(Chat parameters) {
		return sqlSession.insert(this.getClass().getName() + ".insert_chat", parameters);
	}
	
	public List<Chat> list_chat(HashMap<String, Object> parameters) throws Exception{
		return sqlSession.selectList(this.getClass().getName() + ".list_chat", parameters);
	}
}
