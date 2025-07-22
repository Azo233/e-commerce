package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.category.CategoryRequest;
import com.azo.ecommerce.model.Category;
import com.azo.ecommerce.repository.CategoryRepository;
import com.azo.ecommerce.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImplementation(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createCategory(CategoryRequest request) {
        if(request.getCategory().getCategoryId() != null && categoryRepository.existsById(request.getCategory().getCategoryId())){
            throw new IllegalArgumentException("Category already exists with ID: " + request.getCategory().getCategoryId());
        }
        return categoryRepository.save(request.getCategory());
    }

    @Override
    public Optional<Category> updateCategory(CategoryRequest request) {
        if(!categoryRepository.existsById(request.getCategory().getCategoryId())){
            throw new IllegalArgumentException("Category does not exist with ID: " + request.getCategory().getCategoryId());
        }
        return Optional.of(categoryRepository.save(request.getCategory()));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if(!categoryRepository.existsById(categoryId)){
            throw new IllegalArgumentException("Category does not exist with ID: " + categoryId);
        }
         categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }
}
