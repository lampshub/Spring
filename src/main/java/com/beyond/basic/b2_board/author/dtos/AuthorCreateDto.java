package com.beyond.basic.b2_board.author.dtos;

import com.beyond.basic.b2_board.author.domain.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
//dto계층은 엔티티만큼의 안정성을 우선하기보다는, 편의를 위해 setter도 일반적으로 추가 -> @Data 사용
@Data
public class AuthorCreateDto {

//    NotEmpty : 비어있으면 안됨을 의미하는 어노테이션
//    NotBlank : "   "(공백)까지 포함해서 검증하는 어노테이션 => 공백도 안됨!
    @NotBlank(message = "이름이 비어있으면 안됩니다.")
    private String name;
    @NotBlank(message = "email이 비어있으면 안됩니다.")
    private String email;
    @NotBlank(message = "password는 비어있으면 안됩니다.")
    @Size(min = 8, message = "패스워드의 길이가 너무 짧습니다.")
    private String password;

//    //객체조립 방법2
//    public Author toEntity() {  //dto->entity
//        return Author.builder()
//                .name(this.name)
//                .email(this.email)
//                .password(this.password)
//                .build();
//    }

    public Author toEntity(String encodedPassword) { 
        return Author.builder()
                .name(this.name)
                .email(this.email)
                .password(encodedPassword)
                .build();
    }
    }
