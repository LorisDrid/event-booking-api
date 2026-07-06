package com.monacoevents.eventbooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monacoevents.eventbooking.dto.CategoryRequest;
import com.monacoevents.eventbooking.dto.CategoryResponse;
import com.monacoevents.eventbooking.exception.ResourceNotFoundException;
import com.monacoevents.eventbooking.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    @Test
    void createsCategory() throws Exception {
        CategoryRequest request = new CategoryRequest("Concert");
        CategoryResponse response = new CategoryResponse(1L, "Concert");

        when(categoryService.create(request)).thenReturn(response);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Concert"));
    }

    @Test
    void rejectsBlankName() throws Exception {
        CategoryRequest request = new CategoryRequest(" ");

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdReturns404WhenMissing() throws Exception {
        when(categoryService.findById(1L)).thenThrow(new ResourceNotFoundException("Category not found with id 1"));

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllReturnsList() throws Exception {
        when(categoryService.findAll()).thenReturn(List.of(new CategoryResponse(1L, "Concert")));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Concert"));
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNoContent());
    }
}
