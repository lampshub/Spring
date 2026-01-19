package com.beyond.basic.b2_board.author.controller;
// controller, repository, service 안보고 다 코드 칠수있을때까지

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
//      생성자주입방식으로 생성자AutoController에 authorService 주입
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService=authorService;
    }
//    public AuthorController(){      //생성자
//        this.authorService = new AuthorService();
//    }

//    컨트롤러에서 서비스로 dto 전달
    @PostMapping("/create")
    public String create(@RequestBody AuthorCreateDto dto){     //AuthorCreateDto에서 레파지토리로 갈때, Author(엔티티)로 바꿔서 줘야 db에 저장할수있음
        authorService.save(dto);
        return "done";
    }

    //    수업영상 260116 오후 3:11:45
    @GetMapping("/list")
    public List<AuthorListDto> findAll(){
//        List<AuthorListDto> dtoList = authorService.findAll();
        AuthorService authorService = new AuthorService();
        List<AuthorListDto> dtoList = authorService.findAll();
        return dtoList;

    }

//    수업영상 260116 오후 2:40:00
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
