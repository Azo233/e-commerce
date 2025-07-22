package com.azo.ecommerce.service;

import com.azo.ecommerce.dto.category.CategoryRequest;
import com.azo.ecommerce.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long categoryId);

    Category createCategory(CategoryRequest request);

    Optional<Category> updateCategory(CategoryRequest request);

    void deleteCategory(Long categoryId);

    Category getCategoryByName(String categoryName);
}
