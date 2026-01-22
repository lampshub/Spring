package com.beyond.basic.study.dtos;

import com.beyond.basic.study.domain.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorCreateDto {

    private String name;
    private String email;
    private String password;

    public Author toEntity() {
        return Author.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
