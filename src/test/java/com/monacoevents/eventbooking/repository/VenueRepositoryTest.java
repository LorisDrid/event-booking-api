package com.monacoevents.eventbooking.repository;

import com.monacoevents.eventbooking.entity.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VenueRepositoryTest {

    @Autowired
    private VenueRepository venueRepository;

    @Test
    void savesAndRetrievesVenue() {
        Venue venue = Venue.builder()
                .name("Grimaldi Forum")
                .address("10 Avenue Princesse Grace")
                .city("Monaco")
                .build();

        Venue saved = venueRepository.save(venue);
        Optional<Venue> found = venueRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getCity()).isEqualTo("Monaco");
    }
}
