package com.blogapp.BlogApp.repository;

import com.blogapp.BlogApp.entities.Category;
import com.blogapp.BlogApp.entities.Post;
import com.blogapp.BlogApp.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser (User user);
    List<Post> findByCategory (Category category);
    List <Post> findByTitleContaining(String title);
}
