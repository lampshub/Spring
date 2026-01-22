package com.beyond.basic.study.domain;

import com.beyond.basic.b2_board.common.BaseTimeEntity;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    길이(varchar50, 디폴트-varchar255), 제약조건(unique, not null) 설정
    @Column(length = 50, unique = true, nullable = false)
    private String email;
    private String password;

//enum 타입 설정. default USER
    private Role role ;

//    post와 관계맺기 - List<Post>

//    address와 관계맺기

//    비밀번호 변경 메서드


}
