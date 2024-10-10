package com.azo.ecommerce.dto.categoy;

import com.azo.ecommerce.model.Category;
import jakarta.persistence.Column;

import java.sql.Timestamp;

public class CategoryRequest {
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
