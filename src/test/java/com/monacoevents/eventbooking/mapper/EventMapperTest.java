package com.monacoevents.eventbooking.mapper;

import com.monacoevents.eventbooking.dto.EventRequest;
import com.monacoevents.eventbooking.dto.EventResponse;
import com.monacoevents.eventbooking.entity.Category;
import com.monacoevents.eventbooking.entity.Event;
import com.monacoevents.eventbooking.entity.Venue;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EventMapperTest {

    private final EventMapper eventMapper = new EventMapperImpl(new VenueMapperImpl(), new CategoryMapperImpl());

    @Test
    void mapsRequestToEntityIgnoringVenueAndCategory() {
        EventRequest request = new EventRequest(
                "Jazz Night",
                "Soirée jazz au Grimaldi Forum",
                LocalDateTime.of(2026, 9, 15, 20, 30),
                500,
                new BigDecimal("45.00"),
                1L,
                2L
        );

        Event event = eventMapper.toEntity(request);

        assertThat(event.getTitle()).isEqualTo("Jazz Night");
        assertThat(event.getCapacity()).isEqualTo(500);
        assertThat(event.getVenue()).isNull();
        assertThat(event.getCategory()).isNull();
    }

    @Test
    void mapsEntityToResponse() {
        Venue venue = Venue.builder()
                .id(1L)
                .name("Grimaldi Forum")
                .address("10 Avenue Princesse Grace")
                .city("Monaco")
                .build();
        Category category = Category.builder().id(2L).name("Concert").build();
        Event event = Event.builder()
                .id(10L)
                .title("Jazz Night")
                .description("Soirée jazz au Grimaldi Forum")
                .eventDate(LocalDateTime.of(2026, 9, 15, 20, 30))
                .capacity(500)
                .price(new BigDecimal("45.00"))
                .venue(venue)
                .category(category)
                .build();

        EventResponse response = eventMapper.toResponse(event);

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.venue().city()).isEqualTo("Monaco");
        assertThat(response.category().name()).isEqualTo("Concert");
    }
}
