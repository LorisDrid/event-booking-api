package com.monacoevents.eventbooking.mapper;

import com.monacoevents.eventbooking.dto.CategoryRequest;
import com.monacoevents.eventbooking.dto.CategoryResponse;
import com.monacoevents.eventbooking.entity.Category;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryMapperTest {

    private final CategoryMapper categoryMapper = new CategoryMapperImpl();

    @Test
    void mapsRequestToEntity() {
        CategoryRequest request = new CategoryRequest("Concert");

        Category category = categoryMapper.toEntity(request);

        assertThat(category.getName()).isEqualTo("Concert");
        assertThat(category.getId()).isNull();
    }

    @Test
    void mapsEntityToResponse() {
        Category category = Category.builder().id(1L).name("Concert").build();

        CategoryResponse response = categoryMapper.toResponse(category);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Concert");
    }
}
