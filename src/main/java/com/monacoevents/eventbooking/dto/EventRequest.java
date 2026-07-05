package com.monacoevents.eventbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventRequest(
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotNull(message = "Event date is required")
        LocalDateTime eventDate,

        @NotNull(message = "Capacity is required")
        @Positive(message = "Capacity must be positive")
        Integer capacity,

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be zero or positive")
        BigDecimal price,

        @NotNull(message = "Venue id is required")
        Long venueId,

        @NotNull(message = "Category id is required")
        Long categoryId
) {
}
