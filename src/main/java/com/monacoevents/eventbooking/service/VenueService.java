package com.monacoevents.eventbooking.service;

import com.monacoevents.eventbooking.dto.VenueRequest;
import com.monacoevents.eventbooking.dto.VenueResponse;

import java.util.List;

public interface VenueService {

    VenueResponse create(VenueRequest request);

    VenueResponse findById(Long id);

    List<VenueResponse> findAll();

    VenueResponse update(Long id, VenueRequest request);

    void delete(Long id);
}
