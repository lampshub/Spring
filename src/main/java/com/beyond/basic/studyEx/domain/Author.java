package com.beyond.basic.studyEx.domain;

public class Author {

    private Long id;
    private String name;
//    길이(varchar50, 디폴트-varchar255), 제약조건(unique, not null) 설정
    private String email;
    private String password;

//enum 타입 설정. default USER
    private Role role ;

//    post와 관계맺기 - List<Post>

//    address와 관계맺기

//    비밀번호 변경 메서드


}
