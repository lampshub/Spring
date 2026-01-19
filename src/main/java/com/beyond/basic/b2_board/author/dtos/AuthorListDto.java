package com.beyond.basic.b2_board.author.dtos;

import com.beyond.basic.b2_board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class AuthorListDto {
    private Long id;
    private String name;
    private String email;

    public static AuthorListDto fromEntity(Author author){
        return AuthorListDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())

                .build();

    }
}

//list목록을 조회할때 password까지 주는게 이상함. -> dto 로 객체 따로 사용