package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.dto.technique.TechniqueCreateRequest;
import com.prosvirnin.trainersportal.model.dto.technique.TechniqueDto;
import com.prosvirnin.trainersportal.model.dto.technique.TechniqueListItem;
import com.prosvirnin.trainersportal.service.technique.TechniqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/technique")
@RequiredArgsConstructor
@Tag(name = "Техники", description = "Управление техниками и их видео")
public class TechniqueController {

    private final TechniqueService techniqueService;

    @Operation(
            summary = "Загрузить технику с видеофайлом",
            description = "Загружает новую технику с описанием и прикреплённым видеофайлом (формат mp4).",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            encoding = {
                                    @Encoding(name = "technique", contentType = MediaType.APPLICATION_JSON_VALUE),
                                    @Encoding(name = "file", contentType = "video/mp4")
                            }
                    )
            )
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TechniqueDto> save(
            @Parameter(
                    description = "Данные о технике",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TechniqueCreateRequest.class))
            )
            @RequestPart("technique") TechniqueCreateRequest request,
            @Parameter(description = "Видеофайл техники (mp4)")
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(techniqueService.save(request, file));
    }

    @Operation(summary = "Получить список всех техник", description = "Возвращает краткую информацию о всех загруженных техниках.")
    @GetMapping
    public ResponseEntity<Collection<TechniqueListItem>> findAll() {
        return ResponseEntity.ok(techniqueService.findAll());
    }

    @Operation(summary = "Получить технику по ID", description = "Возвращает полную информацию о технике по её идентификатору.")
    @GetMapping("/{id}")
    public ResponseEntity<TechniqueDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(techniqueService.findById(id));
    }
}

