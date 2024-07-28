package com.blogapp.BlogApp.services.impl;

import com.blogapp.BlogApp.config.AppConstants;
import com.blogapp.BlogApp.entities.Role;
import com.blogapp.BlogApp.entities.User;
import com.blogapp.BlogApp.exceptions.ResourceNotFoundException;
import com.blogapp.BlogApp.payloads.UserDto;
import com.blogapp.BlogApp.repository.RoleRepo;
import com.blogapp.BlogApp.repository.UserRepo;
import com.blogapp.BlogApp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser= this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        Optional<User> optionalUser = userRepo.findById(userId);
        User user=optionalUser.orElseThrow(() -> new ResourceNotFoundException("User","id",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());

        User updateduser=this.userRepo.save(user);

        return this.userToDto(updateduser);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        Optional<User> optionalUser = userRepo.findById(userId);
        User user=optionalUser.orElseThrow(() -> new ResourceNotFoundException("User","id",userId));

        return this.userToDto(user);

    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users =this.userRepo.findAll();

        return users.stream().map(this::userToDto).toList();
    }

    @Override
    public void deleteUser(Integer userId) {

        Optional<User> optionalUser = userRepo.findById(userId);
        User user=optionalUser.orElseThrow(() -> new ResourceNotFoundException("User","id",userId));

        this.userRepo.delete(user);
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user=this.modelMapper.map(userDto,User.class);
        //Encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        //roles
        Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser=this.userRepo.save(user);

        return this.modelMapper.map(newUser,UserDto.class);
    }

    //Method to convert DTO object into entity object
    private User dtoToUser (UserDto userDto){

        //Manual conversion steps
        /*user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());*/

        return this.modelMapper.map(userDto,User.class);

    }

    //Method to Convert entity to DTO
    private UserDto userToDto(User user){

        return this.modelMapper.map(user,UserDto.class);
    }
}
