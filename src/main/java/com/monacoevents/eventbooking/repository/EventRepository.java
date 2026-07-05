package com.monacoevents.eventbooking.repository;

import com.monacoevents.eventbooking.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
