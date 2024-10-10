package com.azo.ecommerce.controller;

import com.azo.ecommerce.dto.categoy.CategoryResponse;
import com.azo.ecommerce.model.Category;
import com.azo.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base-url}/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/getAllCategorys")
    public List<Category> getAllCatgories () {
        return categoryService.getAllCategories();
    }

    @GetMapping("/getCategoryById")
    public ResponseEntity<CategoryResponse> getCategoryById(@RequestParam Long categoryId) {
        Optional<Category> optionalCategory = categoryService.getCategoryById(categoryId);

        if (optionalCategory.isPresent()) {
            // If present, create a new response object
            Category category = optionalCategory.get();
            CategoryResponse response = new CategoryResponse(
                    category.getCategoryId(),
                    category.getName(),
                    category.getDescription(),
                    category.getCreatedAt()
            );

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
