package com.monacoevents.eventbooking.repository;

import com.monacoevents.eventbooking.entity.Category;
import com.monacoevents.eventbooking.entity.Event;
import com.monacoevents.eventbooking.entity.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void savesAndRetrievesEvent() {
        Venue venue = entityManager.persist(Venue.builder()
                .name("Grimaldi Forum")
                .address("10 Avenue Princesse Grace")
                .city("Monaco")
                .build());
        Category category = entityManager.persist(Category.builder().name("Concert").build());

        Event event = Event.builder()
                .title("Jazz Night")
                .description("Soirée jazz au Grimaldi Forum")
                .eventDate(LocalDateTime.of(2026, 9, 15, 20, 30))
                .capacity(500)
                .price(new BigDecimal("45.00"))
                .venue(venue)
                .category(category)
                .build();

        Event saved = eventRepository.save(event);
        Optional<Event> found = eventRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Jazz Night");
        assertThat(found.get().getVenue().getCity()).isEqualTo("Monaco");
        assertThat(found.get().getCategory().getName()).isEqualTo("Concert");
    }
}
