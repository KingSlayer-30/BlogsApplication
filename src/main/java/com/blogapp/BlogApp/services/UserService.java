package com.blogapp.BlogApp.services;

import com.blogapp.BlogApp.entities.User;
import com.blogapp.BlogApp.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser (UserDto userDto, Integer userId);
    UserDto getUserById (Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
