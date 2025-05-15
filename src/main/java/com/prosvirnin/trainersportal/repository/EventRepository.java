package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.event.Event;
import com.prosvirnin.trainersportal.model.dto.event.EventListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.event.EventListItem(
            e.id, e.name, e.description)
        FROM Event e
    """)
    List<EventListItem> getAllListItems();
}
