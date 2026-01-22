package com.beyond.basic.study.controller;

import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/response_entity")
public class ResponseEntityController {

//    @ResponseStatus 어노테이션 사용 : 상황에 따른 분기처리의 어려움
    @ResponseStatus(HttpStatus.CREATED) //Ok : 200, CREATED : 201(등록성공), BAD_REQUEST  요청 문법 잘못됐을때 : 400, NOTFOUND : 404
    @GetMapping("/annotation")
    public String annotation(){
        return "ok";
    }

//    ResponseEntity 방식
    @GetMapping("/method1")
    public ResponseEntity<String> method1(){
        return new ResponseEntity<>("OK",HttpStatus.CREATED);     //BODY, header, 상태코드(enom타입)
    }
    @GetMapping("/method2")
    public ResponseEntity<?> method2(){      // Object와 비슷한 ? 사용
        return new ResponseEntity<>("OK",HttpStatus.NOT_FOUND);
    }
//      가장 추천하는 방식
//    ResponseEntity, ?(any), 빌더패턴을 사용하여 status 상태코드, header, body를 쉽게 생성
    @GetMapping("/method3")
    public ResponseEntity<?> method3(){      // Object와 비슷한 ? 사용
        AuthorDetailDto dto = AuthorDetailDto.builder()
                .id(1L).name("hong").email("hong@naver.com").password("1234")
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
//                .header("Content-Type", "application/json")   //세팅할수있지만 바꿀일이 거의 없음
                .body(dto);  //build패턴
    }

    @GetMapping("/method4")
    public ResponseEntity<?> method4(){
        AuthorDetailDto dto = AuthorDetailDto.builder()
                .id(1L).name("hong").email("hong@naver.com").password("1234")
                .build();
        return ResponseEntity.ok(dto);  // .상태코드(body)
    }

}
