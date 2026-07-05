package com.monacoevents.eventbooking.mapper;

import com.monacoevents.eventbooking.dto.EventRequest;
import com.monacoevents.eventbooking.dto.EventResponse;
import com.monacoevents.eventbooking.entity.Event;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {VenueMapper.class, CategoryMapper.class})
public interface EventMapper {

    @Mapping(target = "venue", ignore = true)
    @Mapping(target = "category", ignore = true)
    Event toEntity(EventRequest request);

    EventResponse toResponse(Event event);
}
