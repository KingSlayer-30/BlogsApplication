package com.blogapp.BlogApp.payloads;

import com.blogapp.BlogApp.entities.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min=4,message = "Username must be min of 4 Chars!")
    private String name;

    @NotEmpty(message = "Please enter email!")
    @Email(message = "Email address is not valid!")
    private String email;

    @NotEmpty()
    @Size(min=3, max=10, message="Password must be between 3 to 10 chars!!")
    private String password;

    @NotEmpty(message = "About section cannot be empty!")
    private String about;

    private Set<RoleDto> roles= new HashSet<>();
}
