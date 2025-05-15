package com.prosvirnin.trainersportal.core.mapper.event;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.event.Event;
import com.prosvirnin.trainersportal.model.dto.event.EventDto;
import org.springframework.stereotype.Component;

@Component
public class EventToEventDtoMapper implements Mapper<Event, EventDto> {
    @Override
    public EventDto map(Event source) {
        return EventDto.builder()
                .id(source.getId())
                .authorId(source.getAuthor().getId())
                .publicationDate(source.getPublicationDate())
                .city(source.getCity())
                .name(source.getName())
                .start(source.getStart())
                .description(source.getDescription())
                .filename(source.getFile().getName())
                .build();
    }
}
