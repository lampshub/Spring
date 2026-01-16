package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private AuthorService authorService;
    public AuthorController(){
        this.authorService = new AuthorService();
    }

    @PostMapping("/create")
    public String create(@RequestBody AuthorCreateDto dto){     //AuthorCreateDto에서 레파지토리로 갈때, Author(엔티티)로 바꿔서 줘야 db에 저장할수있음
        authorService.save(dto);
        return "done";
    }

    @GetMapping("/list")
    public List<AuthorListDto> findAll(){
        List<AuthorListDto> dtoList = authorService.findAll();
        return dtoList;
    }

    @GetMapping("/{id}")
    public AuthorDetailDto findById(@PathVariable Long id){
//        서비스에 id값 조회 호출
        AuthorDetailDto dto = authorService.findById(id);
        return dto;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        System.out.println(id);
        return "OK";
    }
}
