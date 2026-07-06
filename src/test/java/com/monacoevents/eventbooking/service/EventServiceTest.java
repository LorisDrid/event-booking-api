package com.monacoevents.eventbooking.service;

import com.monacoevents.eventbooking.dto.EventRequest;
import com.monacoevents.eventbooking.dto.EventResponse;
import com.monacoevents.eventbooking.entity.Category;
import com.monacoevents.eventbooking.entity.Event;
import com.monacoevents.eventbooking.entity.Venue;
import com.monacoevents.eventbooking.exception.ResourceNotFoundException;
import com.monacoevents.eventbooking.mapper.EventMapper;
import com.monacoevents.eventbooking.repository.CategoryRepository;
import com.monacoevents.eventbooking.repository.EventRepository;
import com.monacoevents.eventbooking.repository.VenueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventServiceImpl eventService;

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
    void createsEventWhenVenueAndCategoryExist() {
        Venue venue = Venue.builder().id(1L).name("Grimaldi Forum").address("addr").city("Monaco").build();
        Category category = Category.builder().id(2L).name("Concert").build();
        Event entity = Event.builder().title("Jazz Night").build();
        Event saved = Event.builder().id(10L).title("Jazz Night").venue(venue).category(category).build();
        EventResponse response = new EventResponse(10L, "Jazz Night", null, null, 500, new BigDecimal("45.00"), null, null);

        when(eventMapper.toEntity(request)).thenReturn(entity);
        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(eventRepository.save(entity)).thenReturn(saved);
        when(eventMapper.toResponse(saved)).thenReturn(response);

        EventResponse result = eventService.create(request);

        assertThat(result).isEqualTo(response);
        assertThat(entity.getVenue()).isEqualTo(venue);
        assertThat(entity.getCategory()).isEqualTo(category);
    }

    @Test
    void createThrowsWhenVenueNotFound() {
        when(eventMapper.toEntity(request)).thenReturn(new Event());
        when(venueRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.create(request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(eventRepository, never()).save(any());
    }

    @Test
    void createThrowsWhenCategoryNotFound() {
        Venue venue = Venue.builder().id(1L).build();
        when(eventMapper.toEntity(request)).thenReturn(new Event());
        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.create(request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(eventRepository, never()).save(any());
    }

    @Test
    void findByIdThrowsWhenNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteThrowsWhenNotFound() {
        when(eventRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> eventService.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(eventRepository, never()).deleteById(any());
    }
}
