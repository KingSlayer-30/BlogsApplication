package com.blogapp.BlogApp.services.impl;

import com.blogapp.BlogApp.entities.Category;
import com.blogapp.BlogApp.exceptions.ResourceNotFoundException;
import com.blogapp.BlogApp.payloads.CategoryDto;
import com.blogapp.BlogApp.repository.CategoryRepo;
import com.blogapp.BlogApp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = this.DtoToCategory(categoryDto);
        Category savedCategory= this.categoryRepo.save(category);
        return this.CategoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Optional<Category> optionalCategory= categoryRepo.findById(categoryId);
        Category category= optionalCategory.orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCat=this.categoryRepo.save(category);

        return this.CategoryToDto(updatedCat);

    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Optional<Category> optionalCategory= categoryRepo.findById(categoryId);
        Category category= optionalCategory.orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        this.categoryRepo.delete(category);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Optional<Category> optionalCategory= categoryRepo.findById(categoryId);
        Category category= optionalCategory.orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));

        return this.CategoryToDto(category);

    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List <Category> categoryList= this.categoryRepo.findAll();
        List <CategoryDto> categoryDtoList=categoryList.stream().map((category) -> this.modelMapper.map(category,CategoryDto.class)).toList();

        return categoryDtoList;

    }


    //Model Mapper Methods
    private CategoryDto CategoryToDto(Category savedCategory) {
        return this.modelMapper.map(savedCategory,CategoryDto.class);
    }
    private Category DtoToCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(categoryDto,Category.class);
    }
}
