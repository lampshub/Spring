package com.beyond.basic.b2_board.post.controller;

import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.dtos.PostSearchDto;
import com.beyond.basic.b2_board.post.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PostController {

    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void postCreate(@RequestBody PostCreateDto dto){
        postService.save(dto);
    }

    @GetMapping("/post/{id}")
    public PostDetailDto postDetailDto(@PathVariable Long id){
        return postService.fingById(id);
    }

//    26.01.27 오후3시수업
    @GetMapping("/posts")
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        postService.delete(id);
//        return ResponseEntity.ok("삭제 완료");
//    public List<PostListDto> findAll() {
//        List<PostListDto> dtoList = postService.findAll();
//        return dtoList;
//    페이징처리를 위한 데이터 요청 형식 : localhost:9090/posts?page=0&size=5&sort=title,asc   //page : 기본디폴트 0
//    public Page<PostListDto> postListDto(@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
//        return postService.findAll(pageable);
//    검색 + 페이징처리를 위한 데이터 요청 형식 : localhost:9090/posts?page=0&size=5&sort=title,asc&title=hello&category=경제
        public Page<PostListDto> postListDto(@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable,@ModelAttribute PostSearchDto searchDto) {
        log.info("dto : {}", searchDto);
        return postService.findAll(pageable, searchDto);


    }

    @DeleteMapping("/post/{id}")
    public void postDelete(@PathVariable Long id){
    }
}
