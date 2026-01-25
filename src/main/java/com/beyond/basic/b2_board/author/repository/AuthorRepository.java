package com.beyond.basic.b2_board.author.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

// SpringDataJpa를 사용하기 위해서는 JpaRepository인터페이스를 상속해야하고, 상속시에 Entity명과 pk타입을 제네릭<>에 설정
// JpaRepository를 상속함으로서 JpaRepository의 주요기능(각종 CRUD)상속
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> { // <엔티티클래스, Id(PK)필드타입>
//    save, findBYId, findAll, delete 등은 사전에 구현

//    수업 26.01.21 오후3시
//    그외에 다른컬럼으로 조회할때에는 findBy+컬럼명 형식으로 선언하면 (런타임)실행시점 자동구현.
    Optional<Author> findByEmail(String email);
    List<Author> findAllByName(String name);
//    List<Author> findAllByNameAndAge(String name, int age); //이렇게도 가능 And/Or

}


