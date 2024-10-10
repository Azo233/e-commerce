package com.azo.ecommerce.dto.categoy;

import com.azo.ecommerce.model.Category;

import java.sql.Timestamp;
import java.util.Optional;

public class CategoryResponse {
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryResponse() {
    }

    public CategoryResponse(Long categoryId, String name, String description, Timestamp createdAt) {
    }

    public Category getCategory() {
        return category;
    }


}
