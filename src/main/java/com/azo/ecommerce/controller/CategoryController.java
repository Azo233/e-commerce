package com.azo.ecommerce.controller;

import com.azo.ecommerce.dto.category.CategoryRequest;
import com.azo.ecommerce.model.Category;
import com.azo.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base-url}/category")
@Tag(name = "Category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/getAllCategorys")
    public List<Category> getAllCatgories () {
        return categoryService.getAllCategories();
    }

    @GetMapping("/getCategoryById")
    public Optional<Category> getCategoryById(@RequestParam Long categoryId) {
        return  categoryService.getCategoryById(categoryId);
    }
    @PostMapping("/createCategory")
    public Category createAdmin(@RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<Category> updateAdmin(@RequestBody CategoryRequest request) {
        Optional<Category> updatedAdmin = categoryService.updateCategory(request);
        return updatedAdmin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<Void> deleteAdmin(@RequestParam Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getCategoryByName")
    public Optional<Category> getCategoryName(@RequestParam String categoryName) {
        return Optional.ofNullable(categoryService.getCategoryByName(categoryName));
    }


}
