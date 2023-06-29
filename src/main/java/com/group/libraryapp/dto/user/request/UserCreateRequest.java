package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {
    private String name;
    private Integer age;  //그냥 Int는 null을 표기할 수 없음.


    //getter
    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
