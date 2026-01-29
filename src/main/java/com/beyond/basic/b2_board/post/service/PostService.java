package com.beyond.basic.b2_board.post.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.dtos.PostSearchDto;
import com.beyond.basic.b2_board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

//    public List<PostListDto> findAll(){
//    public Page<PostListDto> findAll(Pageable pageable){
    public Page<PostListDto> findAll(Pageable pageable, PostSearchDto searchDto){       //Specification조립시 검색Dto 사용
//        List<Post> postList = postRepository.findAllByDelYn("N"); //jpa 가 만들어놓은
//        List<Post> postList = postRepository.fetchInnerJoin();    //jpql을 활용한 직접 만든 쿼리 innerjoinfetch 활용
//        List<PostListDto> postListDtoList = new ArrayList<>();
//        for(Post p : postList){
//              PostListDto dto = PostListDto.fromEntity(p);
//            postListDtoList.add(dto);
//        }
//        return postListDtoList;
//        Page<Post> postList = postRepository.findAll(pageable);

//        검색을 위한 Specification 객체 조립 / 인터페이스라서 직접 구현
        Specification<Post> specification = new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.equal(root.get("delYn"), "N"));
                predicateList.add(criteriaBuilder.equal(root.get("appointment"), "N")); //N인거 찾아서 Y로 바꿔주기위해.
//                root : 엔티티의 컬럼명을 접근하기 위한 객체, criteriaBuilder: 쿼리를 생성하기위한 객체(if분기에 따른 쿼리 조립)
                if(searchDto.getTitle() != null) {
                    predicateList.add(criteriaBuilder.like(root.get("title"), "%"+ searchDto.getTitle() +"%"));
                }
                if(searchDto.getCategory() != null){
                    predicateList.add(criteriaBuilder.equal(root.get("category"), searchDto.getCategory()));
                }
                if(searchDto.getContents() != null){
                    predicateList.add(criteriaBuilder.like(root.get("Contents"), "%"+ searchDto.getContents() +"&"));
                }
                Predicate[] predicateArr = new Predicate[predicateList.size()];
                for(int i=0; i<predicateArr.length; i++){
                    predicateArr[i] = predicateList.get(i);
                }
//                Predicate에는 검색조건들이 담길것이고, 이 Predicate list를 한줄의 predicate로 조립.
                Predicate predicate = criteriaBuilder.and(predicateArr) ;

                return predicate;
            }
        };

        Page<Post> postList = postRepository.findAll(specification, pageable);
//        Page객체 안에 Entity->Dto로 쉽게 변환할수 있는 편의 제공 / List가 아니므로 위에 for문으로 안에 값을 넣는게 안됨
        Page<PostListDto> dto = postList.map(p->PostListDto.fromEntity(p));
        return dto;     // return postList.map(p->PostListDto.fromEntity(p));

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
