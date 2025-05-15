package com.prosvirnin.trainersportal.core.mapper.event;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.event.Event;
import com.prosvirnin.trainersportal.model.dto.event.EventCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class EventCreateRequestToEventMapper implements Mapper<EventCreateRequest, Event> {
    @Override
    public Event map(EventCreateRequest source) {
        return Event.builder()
                .city(source.getCity())
                .description(source.getDescription())
                .name(source.getName())
                .start(source.getStart())
                .build();
    }
}
