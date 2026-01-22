package com.beyond.basic.study.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorUpdatePwDto {

    private String email;
    private String updatePw;


}
