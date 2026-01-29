package com.beyond.basic.b2_board.post.repository;

import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    private List<Post> postList;
//    private static Long staticId =1L;

    List<Post> findAllByDelYn(String delYn);
//    Optional<Post> findByEmail(String email);


//    List<Post> findAllByAuthorIdAndDelYn(Long authorId, String delYn);

//    jpql을 활용한 일반 inner join : N+1문제 해결 X  => 필터링만 한것
//      jpql과 raw쿼리의 차이
//      1. jpql을 사용한 inner join시, 별도의 on조건 필요X
//      2. jpql은 컴파일타임에 에러를 check
//      순수 raw : select p.* from post p inner join author a on a.id=p.author_id;
//    @Query("select p from Post p inner join p.author")  //innerjoin을 하지만 p에서만 가지고 오고 있기에 문제해결이 안됨
//    List<Post> findAllInnerJoin(); //위의 쿼리가 지정한 메서드으로 들어옴.

    @Query("select p from Post p inner join p.author")
    List<Post> findAllInnerJoin();

//    jpql을 활용한 inner join(fetch) : N+1문제 해결 O , 문법오류가 있을때 컴파일에러가 남(장점)
//      순수 raw : select * from post p inner join author a on a.id=p.author_id;
    @Query("select p from Post p inner join fetch p.author")
    List<Post> fetchInnerJoin();

//    Page객체 안에는 content(List<Post>), totalPages, totalElement등의 정보 포함
//    Page<Post> findAll(Pageable pageable);  //pageable : page사이즈, page넘버, 정렬 정보가 들어가있음. Page<Post> : 전체페이지정보, 현재페이지정보 등 들어있음
//  Page<Post> findAllByDelYn(Pageable pageable, String delYn);

//    검색 + 페이징처리까지 할 경우, 아래와 같이 매개변수 선언. (Specification, Pageable 순서 - SimpleJpaRepository에서 정의)
//    @EntityGraph(attributePaths = "author") -> fetch join 어노테이션
    Page<Post> findAll(Specification<Post> specification, Pageable pageable);


//    예약글쓰기를 위한 findAllByAppointment 별도추가
//    List<Post> findAllByAppointment(String appointment);
    List<Post>  findGByAppointment(String appointment);

}
