package com.blogapp.BlogApp.repository;

import com.blogapp.BlogApp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {



}
