package com.blogapp.BlogApp.controller;

import com.blogapp.BlogApp.config.AppConstants;
import com.blogapp.BlogApp.entities.Post;
import com.blogapp.BlogApp.payloads.ApiResponse;
import com.blogapp.BlogApp.payloads.PostDto;
import com.blogapp.BlogApp.payloads.PostResponse;
import com.blogapp.BlogApp.services.FileService;
import com.blogapp.BlogApp.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //CREATE
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
         PostDto newPost= this.postService.createPost(postDto,userId,categoryId);
         return new ResponseEntity<PostDto>(newPost, HttpStatus.CREATED);
    }

    //GET BY CATEGORY
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List <PostDto> postDtos=this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    //GET BY USER ID
    @GetMapping("user/{userId}/posts")
    public ResponseEntity <List<PostDto>> getPostByUser(@PathVariable Integer userId){
        List<PostDto> postResponse=this.postService.getPostByUser(userId);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //GET ALL Post
    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value="page  Size",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){
        PostResponse postResponse=this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    //GET POST BY ID
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostByID(@PathVariable Integer postId){
        PostDto postDto=this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.FOUND);
    }

    //DELETE POST BY ID
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully",true), HttpStatus.OK);
    }

    //UPDATING existing Post
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto postDto1=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(postDto1,HttpStatus.OK);
    }

    //Search API
    @GetMapping("/search/{keywords}")
    public ResponseEntity <List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
    List <PostDto> result=this.postService.searchPosts(keywords);
    return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
    }

    //POST Image upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable("postId") Integer postId) throws IOException {

        PostDto postDto=this.postService.getPostById(postId);
        String fileName=this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatedPost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
    }

    //method to serve files
    @GetMapping(value = "/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream())   ;

    }
}
