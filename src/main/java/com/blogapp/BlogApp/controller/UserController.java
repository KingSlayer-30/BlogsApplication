package com.blogapp.BlogApp.controller;

import com.blogapp.BlogApp.entities.User;
import com.blogapp.BlogApp.payloads.ApiResponse;
import com.blogapp.BlogApp.payloads.UserDto;
import com.blogapp.BlogApp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //POST CREATE USER
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        UserDto createUserDto= this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT update USER
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){

        UserDto updateUser=this.userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updateUser);
    }

    // DELETE only for ADMIN
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){

        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successfully",true), HttpStatus.OK);
    }

    //GET all users
    @GetMapping("/")
    public ResponseEntity <List<UserDto>> getAllUsers(){

        return ResponseEntity.ok((this.userService.getAllUsers()));

    }

    @GetMapping("/{userId}")
    public ResponseEntity <UserDto> getSingleUser(@PathVariable Integer userId){

        UserDto singleUser= this.userService.getUserById(userId);
        return ResponseEntity.ok(singleUser);
    }

}
