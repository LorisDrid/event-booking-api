package com.monacoevents.eventbooking.service;

import com.monacoevents.eventbooking.dto.EventRequest;
import com.monacoevents.eventbooking.dto.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {

    EventResponse create(EventRequest request);

    EventResponse findById(Long id);

    Page<EventResponse> findAll(Pageable pageable);

    EventResponse update(Long id, EventRequest request);

    void delete(Long id);
}
