package com.monacoevents.eventbooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monacoevents.eventbooking.dto.EventRequest;
import com.monacoevents.eventbooking.dto.EventResponse;
import com.monacoevents.eventbooking.exception.ResourceNotFoundException;
import com.monacoevents.eventbooking.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventService eventService;

    private final EventRequest request = new EventRequest(
            "Jazz Night",
            "Soirée jazz au Grimaldi Forum",
            LocalDateTime.of(2026, 9, 15, 20, 30),
            500,
            new BigDecimal("45.00"),
            1L,
            2L
    );

    @Test
    void createsEvent() throws Exception {
        EventResponse response = new EventResponse(10L, "Jazz Night", null, null, 500, new BigDecimal("45.00"), null, null);

        when(eventService.create(request)).thenReturn(response);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("Jazz Night"));
    }

    @Test
    void rejectsBlankTitle() throws Exception {
        EventRequest invalid = new EventRequest(" ", null, LocalDateTime.now(), 500, new BigDecimal("45.00"), 1L, 2L);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsNegativePrice() throws Exception {
        EventRequest invalid = new EventRequest("Jazz Night", null, LocalDateTime.now(), 500, new BigDecimal("-1"), 1L, 2L);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdReturns404WhenMissing() throws Exception {
        when(eventService.findById(1L)).thenThrow(new ResourceNotFoundException("Event not found with id 1"));

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isNotFound());
    }
}
