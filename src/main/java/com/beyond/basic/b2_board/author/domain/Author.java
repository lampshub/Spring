package com.beyond.basic.b2_board.author.domain;

import jakarta.persistence.*;
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
//JPA에게 Entity(도메인)관리를 위임하기 위한 어노테이션 (yml에서 jpa)
@Entity
public class Author {
    @Id //pk설정 : primary key(id)
//    identity : auto_increament설정.  auto : id생성전략을  jpa에게 자동설정하도록 위임.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    Long -> bigint, String -> varchar
    private Long id;
//    변수명이 컬럼명으로 그대로 생성. 캐멀케이스(camelCase)인 경우 DB에는 언더스코어로 변경 ex) nickName -> nick_name
    private String name;
//    길이(varchar50, 디폴트-varchar255), 제약조건(unique, not null) 설정
    @Column(length = 50, unique = true, nullable = false)
    private String email;
//    @Column(name = "pw") : 컬럼명의 변경이 가능하나, 일반적으로 일치시킴.
//    @Setter
    private String password;

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }


}
