package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import net.bytebuddy.pool.TypePool;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    //생성자를 통해 레포지토리 주입받기
    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request){
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request){
        //1. 책 정보 가져오기(이름 기준으로) -> 책이 있다면 책 객체가 book으로 들어옴
       Book book = bookRepository.findByName(request.getBookName())
                    .orElseThrow(IllegalArgumentException::new);

        //2. 대출기록 정보 확인해서 대출중인지 확인하기
        //3. 만약 확인했는데 대출 중이라면 예외 발생시키기
       if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
           throw new IllegalArgumentException("이미 대출되어 있는 책입니다");
       }

       //4. 유저 정보 가져오기
        User user = userRepository.findByName(request.getUserName())
            .orElseThrow(IllegalArgumentException::new);
        user.loanBook(book.getName());

        //5. 유저 정보와 책 정보를 기반으로 UserLoanHistory를 저장
        userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));
        }


        //책 반납
        @Transactional
                public void returnBook(BookReturnRequest request){
            User user = userRepository.findByName(request.getUserName())
                    .orElseThrow(IllegalArgumentException::new);


            //UserLoanHistory  객체의 isReturn값이 true로 바뀜
            user.returnBook(request.getBookName());
          //트랜잭션의 영속성 컨텍스트때문에 save코드 안 써도됨


       }
}
