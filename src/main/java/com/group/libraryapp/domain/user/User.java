package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    @Column(nullable = false, length = 20, name = "name") // name varchar(20)
    private String name;
    private Integer age;

    // OneToMany = 1:n관계 = ManyTwoOne의 반대

    //casecade: 유저가 저장되거나 삭제될때 연결시켜둔 UserLoanHistory 까지 저장되거나 삭제됨
    //orphanRemoval: 유저가 존재하는 상황에서 단순히 UserLoanHistory에 객체 연결을 끊게되면 DB에서도 삭제되는 옵션
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) //1:n
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    protected User() {} //매개변수가 없는 기본생성자

    public User(String name, Integer age) {
        //이름값이 null이거나 비어있을 때는 예외를 던져서 user자체가 생성이 안되고, 저장도 안됨.
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }

        this.name = name;
        this.age = age;
    }

    //getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    //변경되는 이름이 들어옴
    public void updateName(String name){
        this.name = name;
    }

    //대출
    public void loanBook(String bookName){
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    //반납
    public void returnBook(String bookName){
        UserLoanHistory targetHistory = this.userLoanHistories.stream()
                .filter(history -> history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        targetHistory.doReturn();
    }

}

// %s: 문자열 포맷팅에서 사용되는 형식지정자 - > 잘못된 name(John)이 들어왔습니다
// (%d는 정수, %f는 부동소수점 숫자를 대체)
