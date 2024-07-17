package com.blogapp.BlogApp.services.impl;

import com.blogapp.BlogApp.entities.Comment;
import com.blogapp.BlogApp.entities.Post;
import com.blogapp.BlogApp.exceptions.ResourceNotFoundException;
import com.blogapp.BlogApp.payloads.CommentDto;
import com.blogapp.BlogApp.payloads.PostDto;
import com.blogapp.BlogApp.repository.CommentRepo;
import com.blogapp.BlogApp.repository.PostRepo;
import com.blogapp.BlogApp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        Comment comment=this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment=this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","CommentId",commentId));
        this.commentRepo.delete(comment);

    }
}
