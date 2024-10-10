package com.azo.ecommerce.dto.categoy;

import com.azo.ecommerce.model.Category;
import jakarta.persistence.Column;

import java.sql.Timestamp;
import java.util.Optional;

public class CategoryResponse {
    private String name;
    private String description;
    private Timestamp createdAt;

    public CategoryResponse() {
    }

    public CategoryResponse(String name, String description, Timestamp createdAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
