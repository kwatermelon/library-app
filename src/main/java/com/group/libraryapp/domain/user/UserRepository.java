package com.group.libraryapp.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JpaRepository를 상속받기
//@Repository 쓰지 않아도 jparepository 상속받는 것만으로 스프링 빈으로 관리됨
public interface UserRepository extends JpaRepository<User, Long> {
    //entity 객체인 User와 User의 id인 Long 들어옴
    //User findByName(String name);

    Optional<User> findByName(String name);

}