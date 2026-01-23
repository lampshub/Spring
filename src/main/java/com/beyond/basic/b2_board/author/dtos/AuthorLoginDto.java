package com.beyond.basic.b2_board.author.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorLoginDto {
    @NotBlank(message = "이메일 필수")
    private String email;
    @NotBlank(message = "비밀번호 입력 필수 ")
    private String password;

}
