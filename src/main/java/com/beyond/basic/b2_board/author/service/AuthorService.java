package com.beyond.basic.b2_board.author.service;


import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.repository.AuthorJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service 안의 Component어노테이션을 통해 싱글톤(단하나의)객체가 생성되고, 스프링에 의해 스프링컨텍스트에서 관리
@Service
////반드시 초기화 되어야하는 필드(final변수 등)를 대상으로 생성자를 자동생성
//@RequiredArgsConstructor
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
    private final AuthorJdbcRepository authorRepository;
//    생성자가 하나밖에 없을때에는 AutoWired생략가능
    @Autowired
    public AuthorService(AuthorJdbcRepository authorRepository){
        this.authorRepository=authorRepository;
    }

////    의존성주입방법3. RequiredArgsConstructor어노테이션 사용
////    반드시 초기화되어야하는 필드를 선언하고, 위 어노테이션 선언시 생성자주입방식으로 의존성이 주입됨
////    단점 : 다형성 설계는 불가
//      private final AuthorRepository authorRepository;


    public void save(AuthorCreateDto dto){
////        방법1. 객체 직접 조립
////        1-1) 생성자만을 활용한 객체 조립
//        Author author = new Author(null, dto.getName(), dto.getEmail(), dto.getPassword());
////        1-2) Builder패턴을 활용한 객체 조립 => Author.java Author자바클래스에 @Builder 어노테이션 사용   ==> 프로젝트시 이걸 사용추천. toEntity,fromEmtity는 좀 어려운 코드가 될수있음
////        장점 : 1)매개변수의 개수의 유연성 2)매개변수의 순서의 유연성
//        Author author = Author.builder()
//                .email(dto.getEmail())
//                .name(dto.getName())
//                .password(dto.getPassword())
//                .build();

//        방법2. toEntity, FromEntity 패턴을 통한 객체 조립
//        객체조립이라는 반복적인 작업을 별도의 코드로 떼어내 공통화
        Author author = dto.toEntity();
        authorRepository.save(author);
    }

    public AuthorDetailDto findById(Long id){
//        AuthorMemoryRepository authorRepository = new AuthorMemoryRepository();
        Optional<Author> optAuthor = authorRepository.findById(id); // Dto > author
        Author author = optAuthor.orElseThrow(()-> new NoSuchElementException("entity is not found"));
////        dto 조립  // author > dto
//        AuthorDetailDto dto = new AuthorDetailDto(author.getId(), author.getName(), author.getEmail(), author.getPassword());
//        AuthorDetailDto dto = AuthorDetailDto.builder()     //AuthorDetailDto 에 @Builder 사용
//                .id(author.getId())
//                .name(author.getName())
//                .email(author.getEmail())
//                .password(author.getPassword())
//                .build();

//        fromEntity는 아직 dto객체가 만들어지지 않은 상태이므로 static메서드로 설계
        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author);
        return dto;

    }

    public List<AuthorListDto> findAll(){
//        List<Author> authorList = authorRepository.findAll(); //authorRepository는 초기값이 세팅되어있음. list는 초기값이 세팅 되어있어서 Optional설정 안함
//        List<AuthorListDto> authorListDtos = new ArrayList<>();
//        for( Author a : authorList){
//            AuthorListDto dto = new AuthorListDto(a.getId(), a.getName(),a.getEmail());
//            authorListDtos.add(dto);
//        }
//        return authorListDtos;
        List<AuthorListDto> authorListDtos = authorRepository.findAll().stream().map(a->AuthorListDto.fromEntity(a)).collect(Collectors.toList());
        return authorListDtos;
    }
}
