package com.monacoevents.eventbooking.repository;

import com.monacoevents.eventbooking.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
