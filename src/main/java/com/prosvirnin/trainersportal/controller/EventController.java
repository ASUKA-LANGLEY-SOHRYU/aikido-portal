package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.dto.event.EventCreateRequest;
import com.prosvirnin.trainersportal.model.dto.event.EventDto;
import com.prosvirnin.trainersportal.model.dto.event.EventEditRequest;
import com.prosvirnin.trainersportal.model.dto.event.EventListItem;
import com.prosvirnin.trainersportal.service.event.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Tag(name = "Мероприятия", description = "Операции с мероприятиями")
public class EventController {

    private final EventService eventService;

    @Operation(
            summary = "Создание мероприятия",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            encoding = {
                                    @Encoding(name = "event", contentType = MediaType.APPLICATION_JSON_VALUE),
                                    @Encoding(name = "file", contentType = "video/mp4")
                            }
                    )
            )
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventDto> createEvent(
            @Parameter(description = "Данные мероприятия",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EventCreateRequest.class)))
            @RequestPart("event") EventCreateRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal UserDetails user) {

        return ResponseEntity.ok(eventService.save(request, file, user));
    }

    @Operation(summary = "Получение мероприятия по ID")
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @Operation(summary = "Список всех мероприятий (DTO)")
    @GetMapping
    public ResponseEntity<Collection<EventDto>> getAll() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @Operation(summary = "Список всех мероприятий (краткий)")
    @GetMapping("/list")
    public ResponseEntity<Collection<EventListItem>> getAllListItems() {
        return ResponseEntity.ok(eventService.findAllListItems());
    }

    @Operation(
            summary = "Редактирование мероприятия",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            encoding = {
                                    @Encoding(name = "event", contentType = MediaType.APPLICATION_JSON_VALUE),
                                    @Encoding(name = "file", contentType = "video/mp4")
                            }
                    )
            )
    )
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventDto> editEvent(
            @PathVariable Long id,
            @Parameter(description = "Редактируемые поля мероприятия",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EventEditRequest.class)))
            @RequestPart("event") EventEditRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        return ResponseEntity.ok(eventService.editById(id, request, file));
    }

    @Operation(summary = "Удаление мероприятия")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
