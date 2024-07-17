package com.blogapp.BlogApp.services;

import com.blogapp.BlogApp.payloads.PostDto;
import com.blogapp.BlogApp.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //create
    PostDto createPost (PostDto postDto,Integer userId, Integer categoryId);

    //Update
    PostDto updatePost(PostDto postDto, Integer postId);

    //DELETE
    void deletePost(Integer postId);

    //GET ALL
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //GET single post by ID
    PostDto getPostById(Integer postId);

    //GET Posts by Category
    List<PostDto> getPostByCategory(Integer categoryId);

    //GET posts by user
    List<PostDto> getPostByUser (Integer userId);

    //Search Posts
    List<PostDto> searchPosts(String keyword);






}
