package com.beyond.basic.b2_board.post.service;

import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(PostCreateDto dto){
        if(postRepository.findByEmail(dto.getAuthorEmail()).isPresent()){
            throw new IllegalArgumentException("이미 post한 id입니다.");
        };

        Post post=dto.toEntity();
        PostRepository.create(post);


        return post;
    }


}
