package com.beyond.basic.b2_board.author.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class AuthorListDto {
    private Long id;
    private String name;
    private String email;
}

//list목록을 조회할때 password까지 주는게 이상함.