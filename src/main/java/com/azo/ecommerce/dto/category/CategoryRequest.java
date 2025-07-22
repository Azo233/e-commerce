package com.azo.ecommerce.dto.category;

import com.azo.ecommerce.model.Category;

public class CategoryRequest {
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
