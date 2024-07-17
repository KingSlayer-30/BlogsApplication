package com.blogapp.BlogApp.services;

import com.blogapp.BlogApp.payloads.CommentDto;
import org.springframework.stereotype.Service;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);

}
