package com.monacoevents.eventbooking.mapper;

import com.monacoevents.eventbooking.dto.VenueRequest;
import com.monacoevents.eventbooking.dto.VenueResponse;
import com.monacoevents.eventbooking.entity.Venue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VenueMapper {

    Venue toEntity(VenueRequest request);

    VenueResponse toResponse(Venue venue);
}
