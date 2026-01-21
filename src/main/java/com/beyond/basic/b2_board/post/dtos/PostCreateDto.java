package com.beyond.basic.b2_board.post.dtos;

import com.beyond.basic.b2_board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostCreateDto {
    private String title;
    private String contents;
    private String category;
    private String authorEmail;

    public Post toEntity(){
        return Post.builder()
                .title(this.title)
                .contents(this.contents)
                .category(this.category)
                .authorEmail(this.authorEmail)
                .build();
    }
}
