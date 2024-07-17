package com.blogapp.BlogApp.services.impl;

import com.blogapp.BlogApp.entities.Category;
import com.blogapp.BlogApp.entities.Post;
import com.blogapp.BlogApp.entities.User;
import com.blogapp.BlogApp.exceptions.ResourceNotFoundException;
import com.blogapp.BlogApp.payloads.PostDto;
import com.blogapp.BlogApp.payloads.PostResponse;
import com.blogapp.BlogApp.repository.CategoryRepo;
import com.blogapp.BlogApp.repository.PostRepo;
import com.blogapp.BlogApp.repository.UserRepo;
import com.blogapp.BlogApp.services.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
        Category category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post newPost=this.postRepo.save(post);

        return this.modelMapper.map(newPost,PostDto.class);
        
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());

        return this.modelMapper.map(this.postRepo.save(post),PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postIdId",postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {

        Sort sort=null;
        if(sortDir.equalsIgnoreCase("ACS")){
            sort=Sort.by(sortBy).ascending();
        }
        else{
            sort=Sort.by(sortBy).descending();
        }

        System.out.println("Received request with pageNumber: " + pageNumber + ", pageSize: " + pageSize + ", sortBy: " + sortBy);

        Pageable p = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost=this.postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent();
        List <PostDto> postDtos=allPosts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).toList();

        PostResponse postResponse= new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user= this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
        List <Post> posts=this.postRepo.findByUser(user);
        List <PostDto> postDtos=posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).toList();

        return postDtos;

    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        List <Post> posts=this.postRepo.findByCategory(category);
        List <PostDto> postDtos=posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).toList();

        return postDtos;
    }


    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=this.postRepo.findByTitleContaining(keyword);
        List <PostDto> postDto=posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).toList();
        return postDto;
    }
}
