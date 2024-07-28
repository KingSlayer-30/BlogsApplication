package com.blogapp.BlogApp.security;

import com.blogapp.BlogApp.entities.User;
import com.blogapp.BlogApp.exceptions.ResourceNotFoundException;
import com.blogapp.BlogApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Loading user from DB
        return this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user","email:"+username,0));
    }
}
