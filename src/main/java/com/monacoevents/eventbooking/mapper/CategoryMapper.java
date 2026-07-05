package com.monacoevents.eventbooking.mapper;

import com.monacoevents.eventbooking.dto.CategoryRequest;
import com.monacoevents.eventbooking.dto.CategoryResponse;
import com.monacoevents.eventbooking.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);
}
