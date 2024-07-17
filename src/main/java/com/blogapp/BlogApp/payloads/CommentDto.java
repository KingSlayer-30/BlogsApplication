package com.blogapp.BlogApp.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private int id;
    private String content;

}
