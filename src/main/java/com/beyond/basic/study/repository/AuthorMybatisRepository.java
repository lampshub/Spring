package com.beyond.basic.study.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository     //싱글톤객체
//Mybatis기술을 사용한 레파지토리를 만들때 필요한 어노테이션
//MyBatis에서 인터페이스와 mapper관계에 있다고 해서 resource폴더안에 mapper폴더로 관리
@Mapper
public interface AuthorMybatisRepository {
    void save(Author author);
    Optional<Author> findById(Long id);
    Optional<Author> findByEmail(String email);
    List<Author> findAll();
    void delete(Long id);
}
