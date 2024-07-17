package com.blogapp.BlogApp.repository;

import com.blogapp.BlogApp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
