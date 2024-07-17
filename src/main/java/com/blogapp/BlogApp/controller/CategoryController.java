package com.blogapp.BlogApp.controller;

import com.blogapp.BlogApp.payloads.ApiResponse;
import com.blogapp.BlogApp.payloads.CategoryDto;
import com.blogapp.BlogApp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity <CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity <CategoryDto> updateCategory (@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId ){
        CategoryDto updatedCategoryDto=this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(updatedCategoryDto,HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity <CategoryDto> getCategory (@PathVariable Integer categoryId){
        CategoryDto categoryDto=this.categoryService.getCategory(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.FOUND);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List <CategoryDto> categoryDtoList= categoryService.getAllCategories();
        return new ResponseEntity<>(categoryDtoList,HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category is Deleted Successfully",true),HttpStatus.OK);
    }




}
