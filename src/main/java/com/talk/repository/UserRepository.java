package com.talk.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.talk.model.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    //추상 메서드 작성 가능

    //메서드 쿼리 메커니즘 방식으로 메서드 이름 기반으로 쿼리를 자동으로 생성(어노테이션 사용X)
    Optional<Users> findByUserIdAndUserPwd(String id, String pwd);
    //->
    // @Query 어노테이션을 사용한 쿼리 예시
//    @Query("SELECT u FROM User u WHERE u.id = :id AND u.pwd = :pwd")
//    Users findByIdAndPwdUsingQuery(@Param("id") String id, @Param("pwd") String pwd);
}
