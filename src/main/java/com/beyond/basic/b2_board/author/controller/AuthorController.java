package com.beyond.basic.b2_board.author.controller;
// controller, repository, service 안보고 다 코드 칠수있을때까지

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.*;
import com.beyond.basic.b2_board.author.service.AuthorService;
import com.beyond.basic.b2_board.common.auth.JwtTokenProvider;
import com.beyond.basic.b2_board.common.dtos.CommonErrorDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/author")
public class AuthorController {
//      생성자주입방식으로 생성자AutoController에 authorService 주입
    private final AuthorService authorService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthorController(AuthorService authorService, JwtTokenProvider jwtTokenProvider) {
        this.authorService = authorService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
//    public AuthorController(){      //생성자
//        this.authorService = new AuthorService();
//    }

//    컨트롤러에서 서비스로 dto 전달
    @PostMapping("/create")
//    dto에 있는 validation어노테이션(@NotBlank)과 @Valid가 한쌍
//    public ResponseEntity<?> create(@RequestBody @Valid AuthorCreateDto dto) {     //AuthorCreateDto에서 레파지토리로 갈때, Author(엔티티)로 바꿔서 줘야 db에 저장할수있음
    public ResponseEntity<?> create(@RequestPart("author") @Valid AuthorCreateDto dto, @RequestPart("profileImage") MultipartFile profileImage) {

////        아래 예외처리는 ExceptionHandler에서 전역적으로 예외처리
//        try {
//            authorService.save(dto);
//            return ResponseEntity.status(HttpStatus.CREATED).body("ok");
//        } catch (IllegalArgumentException e){
//            e.printStackTrace();
//            CommonErrorDto commonErrorDto = CommonErrorDto.builder().status_code(400).error_message(e.getMessage()).build();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonErrorDto);
//        }

        authorService.save(dto, profileImage);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

//    수업영상 260116 오후 3:11:45
    @GetMapping("/list")
//    PreAuthorize : Authentication객체 안의 권한정보를 확인하는 어노테이션
//    2개 이상의 Role을 허용하는 경우 : "hasRole('ADMIN')  or hasRole('SELLER')"
    @PreAuthorize("hasRole('ADMIN')") // 이 권한이 있어야 한다 -> ROLE_ 에서 찾음    ==> Filter계층인 필터체인-인증/인가관련 예외처리에서 에러를 잡음 SecurityConfig
    public List<AuthorListDto> findAll() {
        List<AuthorListDto> dtoList = authorService.findAll();
        return dtoList;

    }

//    //    수업영상 260116 오후 2:40:00
//    아래와 같이 http응답 body를 분기처리한다 하더라도 상태코드는 200으로 고정(->문제점)
//    @GetMapping("/{id}")
//    public Object findById(@PathVariable Long id) {     //Object는 모든 클래스의 조상
////        서비스에 id값 조회 호출
//        try {
//            AuthorDetailDto dto = authorService.findById(id);
//            return dto;
//        } catch (NoSuchElementException e) {
//            e.printStackTrace();    // 개발자가 보려고 남기는 로그
//            return CommonErrorDto.builder()
//                    .status_code(404)
//                    .error_message(e.getMessage())
//                    .build();
//        }

        @GetMapping("/{id}")
        public ResponseEntity<?> findById(@PathVariable Long id) {
            try {
                AuthorDetailDto dto = authorService.findById(id);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                CommonErrorDto dto = CommonErrorDto.builder()
                    .status_code(404)
                    .error_message(e.getMessage())
                    .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
            }
        }


    //    수업 26.01.20 오전 9:23
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        authorService.delete(id);
        return "OK";
    }

//    이메일을 찾아서 비밀번호 수정
    @PatchMapping("/update/password")
    public void updatePw(@RequestBody AuthorUpdatePwDto dto){
        authorService.updatePw(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthorLoginDto dto){
        Author author = authorService.login(dto);
//        토큰생성 및 리턴
        String token = jwtTokenProvider.createToken(author);
        return token;
    }

//    @PostMapping("/login")
//    public String login(@RequestBody AuthorLoginDto dto){
//        authorService.login(dto);   //author 에 담을필요없으므로 객체생성X -> 토큰생성시 필요시 객체생성해서 사용
////        String token
//    return "token";

@GetMapping("/myinfo")
public ResponseEntity<?> myinfo(@AuthenticationPrincipal String principal) {
    System.out.println(principal);
    AuthorDetailDto dto = authorService.myinfo();
    return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
