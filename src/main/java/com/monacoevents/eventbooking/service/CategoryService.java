package com.monacoevents.eventbooking.service;

import com.monacoevents.eventbooking.dto.CategoryRequest;
import com.monacoevents.eventbooking.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    CategoryResponse findById(Long id);

    List<CategoryResponse> findAll();

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);
}
