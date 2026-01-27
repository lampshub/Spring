package com.beyond.basic.b2_board.post.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostCreateDto dto){
//        Author author =  authorRepository.findByEmail(dto.getAuthorEmail()).orElseThrow(()-> new EntityNotFoundException("중복이메일"));
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(); //filter 에서 set한 principal
        System.out.println(email);
        Author author =  authorRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("중복이메일"));
        postRepository.save(dto.toEntity(author));
    }

    public PostDetailDto fingById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("entity is not found"));
//        Author author = authorRepository.findById(post.getAuthorId()).orElseThrow(()->new EntityNotFoundException("entity is not found")) ;
//        PostDetailDto postDetailDto = PostDetailDto.fromEntity(post, author);
        PostDetailDto postDetailDto = PostDetailDto.fromEntity(post);
        return postDetailDto;
    }

    public List<PostListDto> findAll(){
//        List<Post> postList = postRepository.findAllByDelYn("N"); //jpa 가 만들어놓은
        List<Post> postList = postRepository.fetchInnerJoin();    //jpql을 활용한 직접 만든 쿼리 innerjoin 활용
        List<PostListDto> postListDtoList = new ArrayList<>();
        for(Post p : postList  ){
              PostListDto dto = PostListDto.fromEntity(p);
            postListDtoList.add(dto);
        }
        return postListDtoList;

////        소프트코딩(소프트설계)
//        return postRepository.findAllByDelYn("N").stream().map(p->PostListDto.fromEntity(p)).collect(Collectors.toList());
    }


    public void delete(Long id){
//        하드코딩 (하드delete)
//        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("없는 이메일"));
//        postRepository.deleteById(post);
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("존재하지 않는 게시글입니다 "));
        post.deletePost();
    }

}
