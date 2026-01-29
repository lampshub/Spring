package com.beyond.basic.b2_board.author.domain;

import com.beyond.basic.b2_board.common.domain.BaseTimeEntity;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//도메인 Author는 원본자료라고 보면 됨
//회원가입 시 name, email, password만 입력하고
//회원조회시 id,name email만 주려면(응답시 dto를 따로 만들어씀)
//각각 다른 클래스(dto객체)를 만들어 사용해야함

//@Data   //@Setter가 포함되어있어서 객체가 안정적이지 못함 -> 수정 가능 => 엔티티에서는 Setter 쓰지 말자는 주의 !
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
//Builder패턴은 AllArgs 생성자 기반으로 동작
@Builder
//JPA에게 Entity(도메인)관리를 위임하기 위한 어노테이션 (yml에서 jpa)
@Entity
public class Author extends BaseTimeEntity {
    @Id //pk설정 : primary key(id)
//    identity : auto_increment설정.  auto : id생성전략을 jpa에게 자동설정하도록 위임.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long -> bigint, String -> varchar
    private Long id;
//    변수명이 컬럼명으로 그대로 생성. 캐멀케이스(camelCase)인 경우 DB에는 언더스코어로 변경되어 저장 ex) nickName -> nick_name
    private String name;
//    길이(varchar50, 디폴트-varchar255), 제약조건(unique, not null) 설정
    @Column(length = 50, unique = true, nullable = false)
    private String email;
//    @Column(name = "pw") : 컬럼명의 변경이 가능하나, 일반적으로 일치시킴.
//    @Setter
    private String password;


//    enum타입은 내부적으로 숫자값을 가지고 있으나, 문자형태로 저장하겠다는 어노테이션
    @Enumerated(EnumType.STRING)
    @Builder.Default        //Default 어노테이션을 사용 안하면 -> 클래스위의 @Builder로 아래 선언된 초기값은 무시됨. role=null;
    private Role role = Role.USER;

//    일반적으로 OneToMany는 선택사항, ManyToOne은 필수사항
//    mappedBy : ManyToOne쪽의 변수명을 문자열로 지정. -> 조회해야할 컬럼을 명시
//    연관관계(fk)의 주인설정 -> 연관관계의 주인은 author 변수를 가지고 있는 Post에 있음을 명시 //author에 fk를 검
//    cascade옵션 : 1)REMOVE : 부모 자료삭제시 자식도 함께 삭제 2)PERSIST : 자료를 새로 생성시 author안에서 post를 만들어서 추가할경우, post도 생성됨 3)ALL(REMOVE,PERSIST 모두 포함)
//    orphanRemoval : 자식의 자식까지 연쇄적으로 삭제해야할 경우 모든 부모에 orphanRemoval = true 옵션 추가 // 연관관계를 끊으면 자식은 삭제됨 (A에서 B를 삭제하면 B의 자식인 C도 삭제됨)
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    // OneToMany 는 Lazy가 디폴트값 / ManyToOne 은 EAGER 가 디폴트
    @Builder.Default    //persist옵션쓸때는 new ArrayList<>()로 초기화 꼭 해야함
    private List<Post> postList = new ArrayList<>();

//    @OneToOne(mappedBy="author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Address address;

    private String profileImageUrl;

    public void updateProfileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }


    public void updatePassword(String newPassword){
        this.password = newPassword;
    }


}
