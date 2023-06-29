package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.User;

import javax.persistence.*;

@Entity
public class UserLoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    //user_loan_history 테이블에 User가 없기 때문에 빨간줄 뜸
    //따라서 ManToOne(n:1)이라는 어노테이션을 붙여줌(내가 다수, 상대방이 한개)
    //대출기록은 여러개, 대출기록 소유하는 사용자는 한명
    @ManyToOne //(n:1)
    private User user;
//    private long userId;
    private String bookName;
    private boolean isReturn; //0이면 false, 1이면 true로 생각!


    protected UserLoanHistory() {

    }
    public UserLoanHistory(User user, String bookName) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = false;
    }

    public void doReturn() {
        this.isReturn = true;
    }

    public String getBookName() {
        return this.bookName;
    }
}
