package com.beyond.basic.b2_board.author.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//도메인 Author는 원본자료라고 보면 됨
//회원가입 시 name, email, password만 입력하고
//회원조회시 id,name email만 주려면(응답시 dto를 따로 만들어씀)
//각각 다른 클래스(dto객체?)를 만들어 사용해야함
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private Long id;
    private String name;
    private String email;
    private String password;
}
