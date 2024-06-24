package com.talk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.model.Users;
import com.talk.repository.UserRepository;

@Service
public class UserService {
	// 생성자 주입방식
//	1. 불변성(final)
//	2. 테스트 용이성
//	3. 순환 의존성 방지
//	4. NPE 방지
    private final UserRepository userRepository;
    
//    // 필드 주입방식
//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public boolean checkUser(String userId, String password) {
        // 2가지 방법중 하나 사용
        Users user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getUserPwd().equals(password)) {
            return true;
        }

        Users user2 = userRepository.findByUserIdAndUserPwd(userId, password).orElse(null);
        if (user2 != null) {
            return true;
        }

        return false;
    }
}
