package com.monacoevents.eventbooking.service;

import com.monacoevents.eventbooking.dto.EventRequest;
import com.monacoevents.eventbooking.dto.EventResponse;

import java.util.List;

public interface EventService {

    EventResponse create(EventRequest request);

    EventResponse findById(Long id);

    List<EventResponse> findAll();

    EventResponse update(Long id, EventRequest request);

    void delete(Long id);
}
