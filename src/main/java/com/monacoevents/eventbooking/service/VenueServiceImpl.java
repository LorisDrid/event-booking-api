package com.monacoevents.eventbooking.service;

import com.monacoevents.eventbooking.dto.VenueRequest;
import com.monacoevents.eventbooking.dto.VenueResponse;
import com.monacoevents.eventbooking.entity.Venue;
import com.monacoevents.eventbooking.exception.ResourceNotFoundException;
import com.monacoevents.eventbooking.mapper.VenueMapper;
import com.monacoevents.eventbooking.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Override
    public VenueResponse create(VenueRequest request) {
        Venue venue = venueMapper.toEntity(request);
        Venue saved = venueRepository.save(venue);
        return venueMapper.toResponse(saved);
    }

    @Override
    public VenueResponse findById(Long id) {
        Venue venue = findVenueOrThrow(id);
        return venueMapper.toResponse(venue);
    }

    @Override
    public Page<VenueResponse> findAll(Pageable pageable) {
        return venueRepository.findAll(pageable).map(venueMapper::toResponse);
    }

    @Override
    public VenueResponse update(Long id, VenueRequest request) {
        Venue venue = findVenueOrThrow(id);
        venue.setName(request.name());
        venue.setAddress(request.address());
        venue.setCity(request.city());
        return venueMapper.toResponse(venueRepository.save(venue));
    }

    @Override
    public void delete(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Venue not found with id " + id);
        }
        venueRepository.deleteById(id);
    }

    private Venue findVenueOrThrow(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));
    }
}
