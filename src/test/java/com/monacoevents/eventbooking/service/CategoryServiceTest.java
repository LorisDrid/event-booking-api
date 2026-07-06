package com.monacoevents.eventbooking.service;

import com.monacoevents.eventbooking.dto.CategoryRequest;
import com.monacoevents.eventbooking.dto.CategoryResponse;
import com.monacoevents.eventbooking.entity.Category;
import com.monacoevents.eventbooking.exception.ResourceNotFoundException;
import com.monacoevents.eventbooking.mapper.CategoryMapper;
import com.monacoevents.eventbooking.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createsCategory() {
        CategoryRequest request = new CategoryRequest("Concert");
        Category entity = Category.builder().name("Concert").build();
        Category saved = Category.builder().id(1L).name("Concert").build();
        CategoryResponse response = new CategoryResponse(1L, "Concert");

        when(categoryMapper.toEntity(request)).thenReturn(entity);
        when(categoryRepository.save(entity)).thenReturn(saved);
        when(categoryMapper.toResponse(saved)).thenReturn(response);

        CategoryResponse result = categoryService.create(request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void findByIdReturnsMappedCategory() {
        Category category = Category.builder().id(1L).name("Concert").build();
        CategoryResponse response = new CategoryResponse(1L, "Concert");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toResponse(category)).thenReturn(response);

        CategoryResponse result = categoryService.findById(1L);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void findByIdThrowsWhenNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findAllReturnsMappedList() {
        Category category = Category.builder().id(1L).name("Concert").build();
        CategoryResponse response = new CategoryResponse(1L, "Concert");

        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(categoryMapper.toResponse(category)).thenReturn(response);

        List<CategoryResponse> result = categoryService.findAll();

        assertThat(result).containsExactly(response);
    }

    @Test
    void updateThrowsWhenNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.update(1L, new CategoryRequest("Concert")))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteThrowsWhenNotFound() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> categoryService.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(categoryRepository, never()).deleteById(any());
    }
}
