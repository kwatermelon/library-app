package com.group.libraryapp.dto.user.response;

import com.group.libraryapp.domain.user.User;

public class UserResponse {

    private long id;
    private String name;
    private Integer age;

    public UserResponse(long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    //두번째 방법(더욱 깔끔하게-> 자바8버전의 스트림을 활용한 코드)
//    public UserResponse(User user){
//        this.id = user.getId();
//        this.name = user.getName();
//        this.age = user.getAge();
//    }

    public UserResponse(long id, User user) {   // name과 age를 domain/User객체로 대신함
        this.id = id;
        this.name = user.getName();
        this.age = user.getAge();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}