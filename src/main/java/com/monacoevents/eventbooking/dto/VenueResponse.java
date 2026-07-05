package com.monacoevents.eventbooking.dto;

public record VenueResponse(
        Long id,
        String name,
        String address,
        String city
) {
}
