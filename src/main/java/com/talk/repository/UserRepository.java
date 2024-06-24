package com.talk.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talk.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    //추상 메서드 작성 가능
}
