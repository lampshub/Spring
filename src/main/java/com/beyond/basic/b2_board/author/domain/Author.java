package com.beyond.basic.b2_board.author.domain;

import lombok.*;

//도메인 Author는 원본자료라고 보면 됨
//회원가입 시 name, email, password만 입력하고
//회원조회시 id,name email만 주려면(응답시 dto를 따로 만들어씀)
//각각 다른 클래스(dto객체?)를 만들어 사용해야함

//@Data   //@Setter가 포함되어있어서 객체가 안정적이지 못함 -> 수정 가능 => 엔티티에서는 Setter 쓰지 말자는 주의 !
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
//Builder패턴은 AllArgs 생성자 기반으로 동작
@Builder
public class Author {
    private Long id;
    private String name;
    private String email;
    private String password;
}
