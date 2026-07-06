package com.monacoevents.eventbooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monacoevents.eventbooking.dto.VenueRequest;
import com.monacoevents.eventbooking.dto.VenueResponse;
import com.monacoevents.eventbooking.exception.ResourceNotFoundException;
import com.monacoevents.eventbooking.service.VenueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VenueController.class)
class VenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VenueService venueService;

    private static final String NAME = "Grimaldi Forum";
    private static final String ADDRESS = "10 Avenue Princesse Grace";
    private static final String CITY = "Monaco";

    @Test
    void createsVenue() throws Exception {
        VenueRequest request = new VenueRequest(NAME, ADDRESS, CITY);
        VenueResponse response = new VenueResponse(1L, NAME, ADDRESS, CITY);

        when(venueService.create(request)).thenReturn(response);

        mockMvc.perform(post("/api/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.city").value(CITY));
    }

    @Test
    void rejectsBlankName() throws Exception {
        VenueRequest request = new VenueRequest(" ", ADDRESS, CITY);

        mockMvc.perform(post("/api/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdReturns404WhenMissing() throws Exception {
        when(venueService.findById(1L)).thenThrow(new ResourceNotFoundException("Venue not found with id 1"));

        mockMvc.perform(get("/api/venues/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllReturnsPage() throws Exception {
        Page<VenueResponse> page = new PageImpl<>(List.of(new VenueResponse(1L, NAME, ADDRESS, CITY)), PageRequest.of(0, 20), 1);
        when(venueService.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/api/venues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(NAME));
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/venues/1"))
                .andExpect(status().isNoContent());
    }
}
