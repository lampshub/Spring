package com.beyond.basic.b2_board.post.controller;

import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @GetMapping("/posts")
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        postService.delete(id);
//        return ResponseEntity.ok("삭제 완료");
    public List<PostListDto> findAll() {
        List<PostListDto> dtoList = postService.findAll();
        return dtoList;



}

    @DeleteMapping("/post/{id}")
    public void postDelete(@PathVariable Long id){

    }
}
