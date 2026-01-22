package com.beyond.basic.study.dtos;

import com.beyond.basic.study.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorListDto {

    private Long id;
    private String name;
    private String email;

    public AuthorListDto toEntity(Author author){
        return AuthorListDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())

                .build();
    }


}
