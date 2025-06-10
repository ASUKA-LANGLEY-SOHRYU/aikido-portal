package com.prosvirnin.trainersportal.service.event;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.exception.api.NotFound;
import com.prosvirnin.trainersportal.model.domain.event.Event;
import com.prosvirnin.trainersportal.model.domain.file.File;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.event.EventCreateRequest;
import com.prosvirnin.trainersportal.model.dto.event.EventDto;
import com.prosvirnin.trainersportal.model.dto.event.EventEditRequest;
import com.prosvirnin.trainersportal.repository.EventRepository;
import com.prosvirnin.trainersportal.service.file.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultEventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private FileService fileService;

    @Mock
    private Mapper<EventCreateRequest, Event> eventCreateRequestEventMapper;

    @Mock
    private Mapper<Event, EventDto> eventEventDtoMapper;

    @InjectMocks
    private DefaultEventService eventService;

    @Captor
    private ArgumentCaptor<Event> eventCaptor;

    private Event testEvent;
    private EventDto testEventDto;
    private EventCreateRequest testCreateRequest;
    private EventEditRequest testEditRequest;
    private File testFile;
    private User testAuthor;
    private MultipartFile testMultipartFile;

    @BeforeEach
    void setUp() {
        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setName("Test Event");
        testEvent.setDescription("Test Description");
        testEvent.setCity("Test City");
        testEvent.setStart(LocalDateTime.now().plusDays(7));
        testEvent.setPublicationDate(LocalDate.now());

        testFile = new File();
        testFile.setId(1L);
        testFile.setName("test-file.pdf");
        testEvent.setFile(testFile);

        testAuthor = new User();
        testAuthor.setId(1L);
        testAuthor.setLogin("testuser");
        testAuthor.setFullName("Test User");
        testEvent.setAuthor(testAuthor);

        testEventDto = new EventDto();
        testEventDto.setId(1L);
        testEventDto.setName("Test Event");
        testEventDto.setDescription("Test Description");
        testEventDto.setCity("Test City");
        testEventDto.setStart(testEvent.getStart());
        testEventDto.setPublicationDate(testEvent.getPublicationDate());
        testEventDto.setAuthorId(testAuthor.getId());

        testCreateRequest = new EventCreateRequest();
        testCreateRequest.setName("New Event");
        testCreateRequest.setDescription("New Description");
        testCreateRequest.setCity("New City");
        testCreateRequest.setStart(LocalDateTime.now().plusDays(14));

        testEditRequest = new EventEditRequest();
        testEditRequest.setName("Updated Event");
        testEditRequest.setDescription("Updated Description");
        testEditRequest.setCity("Updated City");
        testEditRequest.setStart(LocalDateTime.now().plusDays(21));

        testMultipartFile = mock(MultipartFile.class);
    }

    @Test
    void findById_shouldReturnEventDto_whenEventExists() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(testEvent));
        when(eventEventDtoMapper.map(testEvent)).thenReturn(testEventDto);

        EventDto result = eventService.findById(eventId);

        assertNotNull(result);
        assertEquals(testEventDto.getId(), result.getId());
        assertEquals(testEventDto.getName(), result.getName());
        
        verify(eventRepository).findById(eventId);
        verify(eventEventDtoMapper).map(testEvent);
    }

    @Test
    void findById_shouldThrowNotFound_whenEventDoesNotExist() {
        Long nonExistentId = 999L;
        when(eventRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        NotFound exception = assertThrows(NotFound.class, () -> eventService.findById(nonExistentId));
        assertTrue(exception.getMessage().contains(nonExistentId.toString()));
        
        verify(eventRepository).findById(nonExistentId);
        verify(eventEventDtoMapper, never()).map(any(Event.class));
    }

    @Test
    void findAll_shouldReturnAllEventDtos() {
        List<Event> events = List.of(testEvent);
        List<EventDto> eventDtos = List.of(testEventDto);
        
        when(eventRepository.findAll()).thenReturn(events);
        when(eventEventDtoMapper.map(events)).thenReturn(eventDtos);

        Collection<EventDto> result = eventService.findAll();

        assertNotNull(result);
        assertEquals(testEventDto, result.iterator().next());
        
        verify(eventRepository).findAll();
        verify(eventEventDtoMapper).map(events);
    }


    @Test
    void editById_shouldNotUpdateFile_whenFileIsNull() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(testEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(eventEventDtoMapper.map(testEvent)).thenReturn(testEventDto);

        EventDto result = eventService.editById(eventId, testEditRequest, null);

        assertNotNull(result);
        
        verify(eventRepository).findById(eventId);
        verify(fileService, never()).save(any());
        verify(fileService, never()).delete(any(String.class));
        verify(eventRepository).save(eventCaptor.capture());
        
        Event capturedEvent = eventCaptor.getValue();
        assertEquals(testFile, capturedEvent.getFile());
    }

    @Test
    void editById_shouldThrowNotFound_whenEventDoesNotExist() {
        Long nonExistentId = 999L;
        when(eventRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        NotFound exception = assertThrows(NotFound.class, 
                () -> eventService.editById(nonExistentId, testEditRequest, testMultipartFile));
        assertTrue(exception.getMessage().contains(nonExistentId.toString()));
        
        verify(eventRepository).findById(nonExistentId);
        verify(fileService, never()).save(any());
        verify(fileService, never()).delete(any(String.class));
        verify(eventRepository, never()).save(any());
    }

    @Test
    void deleteById_shouldDeleteEvent() {
        Long eventId = 1L;

        eventService.deleteById(eventId);

        verify(eventRepository).deleteById(eventId);
    }
}