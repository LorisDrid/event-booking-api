package com.monacoevents.eventbooking.service;

import com.monacoevents.eventbooking.dto.VenueRequest;
import com.monacoevents.eventbooking.dto.VenueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenueService {

    VenueResponse create(VenueRequest request);

    VenueResponse findById(Long id);

    Page<VenueResponse> findAll(Pageable pageable);

    VenueResponse update(Long id, VenueRequest request);

    void delete(Long id);
}
