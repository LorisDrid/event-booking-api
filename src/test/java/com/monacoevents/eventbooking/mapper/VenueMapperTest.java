package com.monacoevents.eventbooking.mapper;

import com.monacoevents.eventbooking.dto.VenueRequest;
import com.monacoevents.eventbooking.dto.VenueResponse;
import com.monacoevents.eventbooking.entity.Venue;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VenueMapperTest {

    private final VenueMapper venueMapper = new VenueMapperImpl();

    @Test
    void mapsRequestToEntity() {
        VenueRequest request = new VenueRequest("Grimaldi Forum", "10 Avenue Princesse Grace", "Monaco");

        Venue venue = venueMapper.toEntity(request);

        assertThat(venue.getName()).isEqualTo("Grimaldi Forum");
        assertThat(venue.getCity()).isEqualTo("Monaco");
        assertThat(venue.getId()).isNull();
    }

    @Test
    void mapsEntityToResponse() {
        Venue venue = Venue.builder()
                .id(1L)
                .name("Grimaldi Forum")
                .address("10 Avenue Princesse Grace")
                .city("Monaco")
                .build();

        VenueResponse response = venueMapper.toResponse(venue);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.city()).isEqualTo("Monaco");
    }
}
