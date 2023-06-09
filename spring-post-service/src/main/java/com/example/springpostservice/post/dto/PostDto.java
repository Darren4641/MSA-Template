package com.example.springpostservice.post.dto;


import com.example.springpostservice.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private String title;
    private String content;
    private String writer;

    @Builder
    public PostDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .writer(writer).build();
    }

}
