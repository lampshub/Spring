package com.beyond.basic.study.controller;
// controller, repository, service 안보고 다 코드 칠수있을때까지

import com.beyond.basic.b2_board.author.dtos.AuthorUpdatePwDto;
import com.beyond.basic.b2_board.common.CommonErrorDto;
import com.beyond.basic.study.domain.Author;
import com.beyond.basic.study.dtos.AuthorCreateDto;
import com.beyond.basic.study.dtos.AuthorDetailDto;
import com.beyond.basic.study.dtos.AuthorListDto;
import com.beyond.basic.study.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //    create 생성
    @PostMapping("/create")
    public void create(@RequestBody AuthorCreateDto dto){
        authorService.save(dto);
    }

//    findAll 생성
    @GetMapping("/list")
    public List<AuthorListDto> findAll(){
        List<AuthorListDto> dtoList = authorService.findAll();
        return dtoList;

    }
//    findById 생성
    @GetMapping("/{id}")
    public AuthorDetailDto findById(@PathVariable Long id){
        AuthorDetailDto dto = authorService.findById(id);

    }

//    delete 생성

//    이메일을 찾아서 비밀번호 수정


}