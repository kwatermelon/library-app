package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceV1 {
    private final UserJdbcRepository userJdbcRepository;

    public UserServiceV1(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;

    }

    public void saveUser(UserCreateRequest request){
        userJdbcRepository.saveUser(request.getName(), request.getAge());
    }


    public List<UserResponse> getUsers(){
        return userJdbcRepository.getUsers();
    }


    public void updateUser(UserUpdateRequest request) {

        //userRepository에 jdbctemplate과 id 넘겨주기!
        boolean isUserNotExist = userJdbcRepository.isUserNotExist(request.getId());
        //user가 존재하지 않는다면 예외처리
        if(isUserNotExist){
            throw new IllegalArgumentException();
        }
        //userRepository에 jdbctemplate, name, id 넘겨주기!
        userJdbcRepository.updateUserName(request.getName(), request.getId());
    }


    public void deleteUser(String name){

        //user가 존재하지 않는다면 예외처리
        if(userJdbcRepository.isUserNotExist(name)){
            throw new IllegalArgumentException();
        }

        userJdbcRepository.deleteUser(name);
}
}