package com.group.libraryapp.dto.user.request;

public class UserUpdateRequest {

    private long id;
    private String name;

    //alt + insert : 생성자, getter, setter


    public UserUpdateRequest(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}