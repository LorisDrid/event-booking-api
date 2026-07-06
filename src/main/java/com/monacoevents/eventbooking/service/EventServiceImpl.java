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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    @Override
    public EventResponse create(EventRequest request) {
        Event event = eventMapper.toEntity(request);
        event.setVenue(findVenueOrThrow(request.venueId()));
        event.setCategory(findCategoryOrThrow(request.categoryId()));
        return eventMapper.toResponse(eventRepository.save(event));
    }

    @Override
    public EventResponse findById(Long id) {
        return eventMapper.toResponse(findEventOrThrow(id));
    }

    @Override
    public Page<EventResponse> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable).map(eventMapper::toResponse);
    }

    @Override
    public EventResponse update(Long id, EventRequest request) {
        Event event = findEventOrThrow(id);
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setEventDate(request.eventDate());
        event.setCapacity(request.capacity());
        event.setPrice(request.price());
        event.setVenue(findVenueOrThrow(request.venueId()));
        event.setCategory(findCategoryOrThrow(request.categoryId()));
        return eventMapper.toResponse(eventRepository.save(event));
    }

    @Override
    public void delete(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with id " + id);
        }
        eventRepository.deleteById(id);
    }

    private Event findEventOrThrow(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    private Venue findVenueOrThrow(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));
    }

    private Category findCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }
}
