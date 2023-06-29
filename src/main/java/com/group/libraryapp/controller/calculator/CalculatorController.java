package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.*;

@RestController //주어진 클래스를 controller로 등록한다.
public class CalculatorController {

    //덧셈
//@GetMapping("/add") // http method가 get이고 http path가 add인 API로 지정한다
//    //1. RequestParam으로 받는방법(param수가 적을 때)
//    public int addTwoNumbers(
//            @RequestParam  int number1,
//            @RequestParam int number2
//){
//    return number1 + number2;
//
//}

    //2. 객체로 받는방법
    @GetMapping("/add")
    public int addTwoNumbers(CalculatorAddRequest request) {
        return request.getNumber1() + request.getNumber2();
    }

    //곱셈 (post)
    //객체로 받기
    //POST 특 : RequestBody를 통해 HTTP body 안에 담긴 json을 객체로 변환해 받을 수 있다.
    @PostMapping("/multiply")
    public int multiplyTwoNumbers(@RequestBody  CalculatorMultiplyRequest request) {
        return request.getNumber1() * request.getNumber2();
    }
}
