package com.beyond.basic.study.dtos;

import com.beyond.basic.b2_board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String email;
    private String password;

//    글 개수도 넣어보기


    public AuthorDetailDto fromEntity(Author author){
        return AuthorDetailDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .password(author.getPassword())
                .build();
    }

}
