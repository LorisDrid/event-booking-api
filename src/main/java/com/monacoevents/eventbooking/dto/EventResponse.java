package com.monacoevents.eventbooking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String description,
        LocalDateTime eventDate,
        Integer capacity,
        BigDecimal price,
        VenueResponse venue,
        CategoryResponse category
) {
}
