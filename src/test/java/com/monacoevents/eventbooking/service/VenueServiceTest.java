package com.monacoevents.eventbooking.service;

import com.monacoevents.eventbooking.dto.VenueRequest;
import com.monacoevents.eventbooking.dto.VenueResponse;
import com.monacoevents.eventbooking.entity.Venue;
import com.monacoevents.eventbooking.exception.ResourceNotFoundException;
import com.monacoevents.eventbooking.mapper.VenueMapper;
import com.monacoevents.eventbooking.repository.VenueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VenueServiceTest {

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private VenueMapper venueMapper;

    @InjectMocks
    private VenueServiceImpl venueService;

    private static final String NAME = "Grimaldi Forum";
    private static final String ADDRESS = "10 Avenue Princesse Grace";
    private static final String CITY = "Monaco";

    @Test
    void createsVenue() {
        VenueRequest request = new VenueRequest(NAME, ADDRESS, CITY);
        Venue entity = Venue.builder().name(NAME).address(ADDRESS).city(CITY).build();
        Venue saved = Venue.builder().id(1L).name(NAME).address(ADDRESS).city(CITY).build();
        VenueResponse response = new VenueResponse(1L, NAME, ADDRESS, CITY);

        when(venueMapper.toEntity(request)).thenReturn(entity);
        when(venueRepository.save(entity)).thenReturn(saved);
        when(venueMapper.toResponse(saved)).thenReturn(response);

        VenueResponse result = venueService.create(request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void findByIdReturnsMappedVenue() {
        Venue venue = Venue.builder().id(1L).name(NAME).address(ADDRESS).city(CITY).build();
        VenueResponse response = new VenueResponse(1L, NAME, ADDRESS, CITY);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(venueMapper.toResponse(venue)).thenReturn(response);

        VenueResponse result = venueService.findById(1L);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void findByIdThrowsWhenNotFound() {
        when(venueRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> venueService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findAllReturnsMappedPage() {
        Venue venue = Venue.builder().id(1L).name(NAME).address(ADDRESS).city(CITY).build();
        VenueResponse response = new VenueResponse(1L, NAME, ADDRESS, CITY);
        Pageable pageable = PageRequest.of(0, 20);
        Page<Venue> venuePage = new PageImpl<>(List.of(venue), pageable, 1);

        when(venueRepository.findAll(pageable)).thenReturn(venuePage);
        when(venueMapper.toResponse(venue)).thenReturn(response);

        Page<VenueResponse> result = venueService.findAll(pageable);

        assertThat(result.getContent()).containsExactly(response);
    }

    @Test
    void updateThrowsWhenNotFound() {
        when(venueRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> venueService.update(1L, new VenueRequest(NAME, ADDRESS, CITY)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteThrowsWhenNotFound() {
        when(venueRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> venueService.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(venueRepository, never()).deleteById(any());
    }
}
