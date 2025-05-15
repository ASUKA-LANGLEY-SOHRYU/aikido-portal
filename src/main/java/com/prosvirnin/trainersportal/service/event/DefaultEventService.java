package com.prosvirnin.trainersportal.service.event;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.exception.api.NotFound;
import com.prosvirnin.trainersportal.model.domain.event.Event;
import com.prosvirnin.trainersportal.model.domain.file.File;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.event.EventCreateRequest;
import com.prosvirnin.trainersportal.model.dto.event.EventDto;
import com.prosvirnin.trainersportal.model.dto.event.EventEditRequest;
import com.prosvirnin.trainersportal.model.dto.event.EventListItem;
import com.prosvirnin.trainersportal.repository.EventRepository;
import com.prosvirnin.trainersportal.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultEventService implements EventService {
    private final EventRepository eventRepository;
    private final FileService fileService;
    private final Mapper<EventCreateRequest, Event> eventCreateRequestEventMapper;
    private final Mapper<Event, EventDto> eventEventDtoMapper;

    @Override
    @Transactional
    public EventDto save(EventCreateRequest request, MultipartFile file, UserDetails author) {
        Event event = eventCreateRequestEventMapper.map(request);
        File eventFile = fileService.save(file);
        event.setFile(eventFile);
        User user = (User) author;
        event.setAuthor(user);
        event.setPublicationDate(LocalDate.now());
        event = eventRepository.save(event);
        return eventEventDtoMapper.map(event);
    }

    @Override
    public EventDto findById(Long id) {
        return eventEventDtoMapper.map(eventRepository.findById(id)
                .orElseThrow(() -> new NotFound("Не удалось найти Event с id: " + id)));
    }

    @Override
    public Collection<EventListItem> findAllListItems() {
        return eventRepository.getAllListItems();
    }

    @Override
    public Collection<EventDto> findAll() {
        return eventEventDtoMapper.map(eventRepository.findAll());
    }

    @Override
    @Transactional
    public EventDto editById(Long id, EventEditRequest request, MultipartFile file) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFound("Не удалось найти Event с id: " + id));
        editEvent(event, request, file);
        event = eventRepository.save(event);
        return eventEventDtoMapper.map(event);
    }

    private void editEvent(Event event, EventEditRequest request, MultipartFile file) {
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setCity(request.getCity());
        event.setStart(request.getStart());

        if (file == null)
            return;

        File eventFile = fileService.save(file);
        fileService.delete(event.getFile());
        event.setFile(eventFile);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }
}
