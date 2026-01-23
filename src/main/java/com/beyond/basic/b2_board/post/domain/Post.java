package com.beyond.basic.b2_board.post.domain;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post  extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 3000)
    private String contents;
    private String category;
//    @Column(nullable = false)
//    private Long authorId;

//    ManyToOne을 통해 fk설정 (author_id 컬럼)
//    ManyToOne을 통해 author_id컬럼으로 author객체 조회 및 객채자동생성
//    fetch(가져오다) lazy (지연로딩) : author객체를 사용하지 않는한, author 객체 생성X (서버부하감소 -> 성능향상)  //EAGER는 엔티티를 조회하는 순간 연관된 엔티티 같이 SELECT(즉시로딩), LAZY는 엔티티만 먼저 조회, 연관객체를 실제로 접근시 SELECT
    @ManyToOne (fetch = FetchType.LAZY)  // post입장에서는 n:1 . author입장에서는 1:n
//    ManyToOne 어노테이션만 추가하더라도, 아래 옵션이 생략돼있는것. fk를 설정하지 않고자 할때, NO_CONSTRANT 설정
    @JoinColumn(name = "author_id", foreignKey=@ForeignKey(ConstraintMode.CONSTRAINT), nullable = false)
    private Author author;  //DB에 객체를 넣을수 없음 -> 객체로 저장시 JPA가 id값만 빼서 입력 시켜줌 / 조회시 author.Repository// .findById 로 찾아서 객체를 찾아줌

    @Builder.Default
    private String delYn="N";


    public void deletePost(){
        this.delYn = "Y";
    }
}
