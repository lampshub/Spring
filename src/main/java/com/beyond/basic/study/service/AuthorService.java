package com.beyond.basic.study.service;


import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.dtos.AuthorUpdatePwDto;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import com.beyond.basic.study.dtos.AuthorCreateDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class AuthorService {

//    save -> email 있는지 검증
    public Author save(AuthorCreateDto dto){
        Author author = Author.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
        return author;
    }

//    findById


//    findAll

//    delete

//    updatePw


}
