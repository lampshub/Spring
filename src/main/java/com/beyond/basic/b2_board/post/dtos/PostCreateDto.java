package com.beyond.basic.b2_board.post.dtos;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostCreateDto {
    @NotBlank   //valid랑 세트    public void postCreate(@RequestBody @Valid PostCreateDto dto){
    private String title;
    private String contents;
    private String category;
//    private String authorEmail;
    @Builder.Default
    private String appointment = "N";
    @Builder.Default
    private LocalDateTime appointmentTime = LocalDateTime.now();


    public Post toEntity(Author author){
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
                .category(this.category)
                .author(author)
//                .authorId(author.getId())
//                .delYn("N")
                .appointment(this.appointment)
                .appointmentTime(this.appointmentTime)
                .build();
    }



}
