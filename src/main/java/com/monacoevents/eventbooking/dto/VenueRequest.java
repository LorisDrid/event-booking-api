package com.monacoevents.eventbooking.dto;

import jakarta.validation.constraints.NotBlank;

public record VenueRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Address is required")
        String address,

        @NotBlank(message = "City is required")
        String city
) {
}
