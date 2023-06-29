package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    //생성자로 스프링빈 주입받기
    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //1. 저장 : save 메소드 호출하기
    @Transactional
    public void saveUser(UserCreateRequest request){
       User u = userRepository.save(new User(request.getName(), request.getAge()));
       //throw new IllegalArgumentException();
        //트랜잭션 적용되어있다면 저장은 성공하는데 예외가 났으니까 저장 자체가 rollback돼서 반영이 안될것
    }

    //2. 조회 -> findAll메소드로 모든 정보 가져오기
    //
    @Transactional
    public List<UserResponse> getUsers(){
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
                .collect(Collectors.toList());  //결과를 리스트로 바꾸기

    }
    //두번째 방법(더욱 깔끔하게-> 자바8버전의 스트림을 활용한 코드)
//    public List<UserResponse> getUsers(){
//        return userRepository.findAll().stream()
//                .map(UserResponse::new)
//                .collect(Collectors.toList());  //결과를 리스트로 바꾸기
//
//    }

    //3. 업데이트
    // ID 찾기 = findById : id를 기준으로 한개의 데이터 가져옴.
    @Transactional
    public void updateUser(UserUpdateRequest request){
        //select * from user where id = ?;
        //결과 : Optional<User>
        //Optional의 orElseThrow를 사용해 User가 없다면 예외를 던진다
        // user가 있는 경우 user로 들어옴.
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);    //유저가 없는경우 예외던지기

        //유저가 존재한다면 객체를 업데이트하고 (updateName 함수) 저장(save함수)
        user.updateName(request.getName());
        //영속성 컨텍스트에 의해 자동 save
        //userRepository.save(user);  //변경된 이름이 저장됨
    }

    //4.삭제 : 이름을 기준으로 있는지 없는지 체크해야함
    // findByName이라는 함수는 없음!
//    @Transactional
//    public void deleteUser(String name){
//        //select * from user where name = ? 만드는 방법은?
//        //1) userRepository 인터페이스에 User findByName(String name); 함수 만들기
//        User user = userRepository.findByName(name);
//        if (user == null){ //존재하지 않는다면 null나옴
//            throw new IllegalArgumentException();
//        }
//        userRepository.delete(user);
//
//    }
}
