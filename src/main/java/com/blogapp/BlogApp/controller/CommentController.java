package com.blogapp.BlogApp.controller;

import com.blogapp.BlogApp.entities.Comment;
import com.blogapp.BlogApp.payloads.ApiResponse;
import com.blogapp.BlogApp.payloads.CommentDto;
import com.blogapp.BlogApp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto createComment=this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<CommentDto>(createComment, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully!!",true),HttpStatus.OK);
    }
}
