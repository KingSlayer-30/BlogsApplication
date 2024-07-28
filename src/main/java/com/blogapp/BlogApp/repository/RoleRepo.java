package com.blogapp.BlogApp.repository;

import com.blogapp.BlogApp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
