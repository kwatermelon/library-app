package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserServiceV1;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    //service호출문장
    private final UserServiceV2 userService;

    public UserController(UserServiceV2 userService) {   //jdbc템플릿 받아서 설정해주는 생성자

        this.userService = userService;
    }

    //C: 유저 저장 API
    @PostMapping("/user") //POST/ user
    public void saveUser(@RequestBody UserCreateRequest request) {  //아무것도 반환할게 없으므로
        userService.saveUser(request);
    }

    //R: 유저조회 API
    @GetMapping("/user")
    //접근제한자     반환타입     메서드이름
    public List<UserResponse> getUsers() { // 반환값 : List<UserResponse> -> 사용자 응답을 담고 있는 UserResponse 객체를 리스트로 반환
        return userService.getUsers();
    }

    //U: 수정
    //json이 들어온걸 객체로 바꾸기 위해서 dto를 만든다. UserUpdateRequest라는 dto
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
        //데이터가 존재하는지 먼저 확인하기
    }
    //D: 삭제
//    @DeleteMapping("/user")
//    //쿼리가 하나라서 RequestParam사용함
//    public void deleteUser(@RequestParam String name) {
//        userService.deleteUser(name);
//    }

}
