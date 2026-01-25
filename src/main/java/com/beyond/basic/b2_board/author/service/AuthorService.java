package com.beyond.basic.b2_board.author.service;


import com.beyond.basic.b2_board.author.controller.AuthorController;
import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.*;
import com.beyond.basic.b2_board.author.repository.*;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service 안의 Component어노테이션을 통해 싱글톤(단하나의)객체가 생성되고, 스프링에 의해 스프링컨텍스트에서 관리
@Service
////반드시 초기화 되어야하는 필드(final변수 등)를 대상으로 생성자를 자동생성
//@RequiredArgsConstructor
//스프링에서 jpa를 활용할때 트랜잭션처리(commit, 롤백) 지원.
//commit 기준점 : 메서드 정상 종료 시점. rollback 기준점 : 예외발생했을 경우.
@Transactional
public class AuthorService {
    private String name;
    private final String email="abc";
////    public AuthorService(){
////        this.AuthorMemoryRepository = new AuthorRepository();
////    }

////    의존성주입(DI) 방법1. 필드주입 : AutoWired 어노테이션 사용 (간편방식)
//    @Autowired
//    private AuthorMemoryRepository authorRepository;


//    의존성주입(DI) 방법2. 생성자주입방식 (가장많이 사용되는 방식)
//    장점1) final을 통해 상수로 사용 가능 (안정성 향상)
//    장점2) 다형성 구현 가능 (interface 사용가능)
//    장점3) 순환참조 방지 (컴파일타임에 에러check)
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
//    생성자가 하나밖에 없을때에는 AutoWired생략가능
    @Autowired
    public AuthorService(AuthorRepository authorRepository, PostRepository postRepository, PasswordEncoder passwordEncoder){
        this.authorRepository=authorRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }

////    의존성주입방법3. RequiredArgsConstructor어노테이션 사용
////    반드시 초기화되어야하는 필드를 선언하고, 위 어노테이션 선언시 생성자주입방식으로 의존성이 주입됨
////    단점 : 다형성 설계는 불가
//      private final AuthorRepository authorRepository;


    public void save(AuthorCreateDto dto){
////        방법1. 객체 직접 조립
////        1-1) 생성자만을 활용한 객체 조립
//        Author author = new Author(null, dto.getName(), dto.getEmail(), dto.getPassword());
////        1-2) Builder패턴을 활용한 객체 조립 => Author.java Author자바클래스에 @Builder 어노테이션 사용   ==> 프로젝트시 이걸 사용추천. toEntity,fromEntity는 좀 어려운 코드가 될수있음
////        장점 : 1)매개변수의 개수의 유연성 2)매개변수의 순서의 유연성
//        Author author = Author.builder()
//                .email(dto.getEmail())
//                .name(dto.getName())
//                .password(dto.getPassword())
//                .build();

//        방법2. toEntity, FromEntity 패턴을 통한 객체 조립
//        객체조립이라는 반복적인 작업을 별도의 코드로 떼어내 공통화
//        email 중복여부 검증     // findByEmail() 리턴타입은 Optional. 있으면 Author, 없으면 empty
        if(authorRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 Email입니다.");
        }       //에러터지면 코드 여기서 스탑 -> CommonExceptionHandler로

//        passwordEncoder.matches(dto.getPassword().db의 패스워드);

//        Author author = dto.toEntity();
        Author author = dto.toEntity(passwordEncoder.encode(dto.getPassword()));     //여기서 author를 만들때 암호화된 값으로 바꿈
        Author authorDb = authorRepository.save(author);    //id 값이 여기 save()로 DB에 저장된 후에 생김
//        cascade persist 를 활용한 예시      // .author(authorDb) 여기서 authorDB의 PK(id)값만 FK로 저장됨 ** 이거 확인해보기
        author.getPostList().add(Post.builder().title("안녕하세요").author(authorDb).build());

////        아래순서로 바꿔도 가능함
//        author.getPostList().add(Post.builder().title("안녕하세요").author(author).build());
//        authorRepository.save(author);    //여기서 id값 생성됨

////        cascade 옵션이 아닌 예시 (cascade없이도 저장 가능)
//        postRepository.save(Post.builder().title("안녕하세요").author(authorDb).build());    //회원가입하면 안녕하세요 post까지 작성.

//        예외 발생시 transactional 어노테이션에 의해 rollback처리 -> 아래 에러로 위에 save도 롤백됨
//        authorRepository.findById(10L).orElseThrow(()-> new NoSuchElementException("entity is not found"));
    }

//    트랙잭션 처리가 필요없는 조회만 있는 메서드의 경우, 성능향상을 위해 readOnly처리
    @Transactional(readOnly = true)
    public AuthorDetailDto findById(Long id){
        Optional<Author> optAuthor = authorRepository.findById(id); // Dto > author
        Author author = optAuthor.orElseThrow(()-> new NoSuchElementException("entity is not found"));
//        List<Post> postList = postRepository.findAllByAuthorIdAndDelYn(author.getId(),"N");


////        dto 조립  // author > dto
//        AuthorDetailDto dto = new AuthorDetailDto(author.getId(), author.getName(), author.getEmail(), author.getPassword());
//        AuthorDetailDto dto = AuthorDetailDto.builder()     //AuthorDetailDto 에 @Builder 사용
//                .id(author.getId())
//                .name(author.getName())
//                .email(author.getEmail())
//                .password(author.getPassword())
//                .build();

//        fromEntity는 아직 dto객체가 만들어지지 않은 상태이므로 static메서드로 설계

//        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author,0);
        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author);
        return dto;
    }


    @Transactional(readOnly = true)
    public List<AuthorListDto> findAll(){
//        List<Author> authorList = authorRepository.findAll(); //authorRepository는 초기값이 세팅되어있음. list는 초기값이 세팅 되어있어서 Optional설정 안함
//        List<AuthorListDto> authorListDtos = new ArrayList<>();
//        for( Author a : authorList){
////            AuthorListDto dto = new AuthorListDto(a.getId(), a.getName(),a.getEmail());
//            AuthorListDto dto = AuthorListDto.fromEntity(a);
//            authorListDtos.add(dto);
//        }
//        return authorListDtos;

        List<AuthorListDto> authorListDtos = authorRepository.findAll().stream().map(a->AuthorListDto.fromEntity(a)).collect(Collectors.toList());
        return authorListDtos;
    }

    public void delete(Long id){
//        데이터 조회 후 없다면 예외처리
        Author author = authorRepository.findById(id).orElseThrow(()-> new NoSuchElementException( "조회하신 아이디가 없습니다."));
//        있으면 삭제작업l
        authorRepository.delete(author);
//        authorRepository.deleteById(id);
    }

    public Author login(AuthorLoginDto dto) {
        Optional<Author> opt_author = authorRepository.findByEmail(dto.getEmail());   //탈취공격시에 없는이메일이라고 에러를 띄우면 이메일이 잘못됐다고 알려주니. 이메일인지 비밀번혼지 안알려주는게 좋음
        boolean check = true;
        if (!opt_author.isPresent()) {
            check = false;
        } else {
            if (!passwordEncoder.matches(dto.getPassword(), opt_author.get().getPassword())) {
                check = false;
            }
        }

        if(!check){
            throw new IllegalArgumentException("email 또는 비밀번호가 일치하지 않습니다.");
        }
        return opt_author.get();    //.get() 은 Optional에서 값을 꺼냄. 없으면 예외발생. 여기선 이미 예외처리를 다 해서 값이 있음.
    }

    public void updatePw(AuthorUpdatePwDto dto){
        Author author = authorRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new EntityNotFoundException("해당 ID가 없습니다"));
        author.updatePassword(dto.getNewPassword());
////        insert, update 모두 save 메서드 사용 -> 변경감지(Dirty Checking)로 대체
//        authorRepository.save(author);

//        영속성컨텍스트 : 애플리케이션과 DB사이에서 객체를 보관하는 가상의 DB 역할
//        장점 1) 쓰기지연 : insert, update 등의 작업사항을 즉시 실행하지 않고, 커밋시점에 모아서 실행(성능향상)
//            2) 변경감지(dirty checking) : 영속상태(managed)의 엔티티는 트랜잭션 커밋시점에 변경감지를 통해 별도의 save없이 DB에 반영
    }
}
