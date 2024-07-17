package com.blogapp.BlogApp.services;

import com.blogapp.BlogApp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDto createCategory (CategoryDto categoryDto);

    //update
    CategoryDto updateCategory (CategoryDto categoryDto,Integer categoryId);

    //delete
    void deleteCategory (Integer categoryId);
    //GET
    CategoryDto getCategory(Integer categoryId);

    //GET ALL
    List<CategoryDto> getAllCategories ();
}
