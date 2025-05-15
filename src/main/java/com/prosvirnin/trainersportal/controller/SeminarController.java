package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.dto.seminar.SeminarCreateRequest;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarDto;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarEditRequest;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarListItem;
import com.prosvirnin.trainersportal.model.dto.user.UserProfileDto;
import com.prosvirnin.trainersportal.service.seminar.SeminarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/seminars")
@RequiredArgsConstructor
@Tag(name = "Семинары", description = "Управление семинарами и ведомостями")
public class SeminarController {

    private final SeminarService seminarService;

    @Operation(summary = "Получить список всех семинаров")
    @GetMapping
    public ResponseEntity<Collection<SeminarListItem>> findAll() {
        return ResponseEntity.ok(seminarService.findAll());
    }

    @Operation(summary = "Создать новый семинар")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody SeminarCreateRequest request) {
        seminarService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Получить семинар по ID")
    @GetMapping("/{id}")
    public ResponseEntity<SeminarDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(seminarService.findById(id));
    }

    @Operation(summary = "Удалить семинар по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        seminarService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновить семинар по ID")
    @PutMapping("/{id}")
    public ResponseEntity<SeminarDto> editById(@PathVariable Long id,
                                               @RequestBody SeminarEditRequest request) {
        return ResponseEntity.ok(seminarService.editById(id, request));
    }

    @Operation(summary = "Скачать временную ведомость (XLSX)")
    @GetMapping("/{id}/report/interim")
    public ResponseEntity<InputStreamResource> getInterimReport(@PathVariable Long id) {
        InputStreamResource resource = seminarService.createInterimReportById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=interim_report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Operation(summary = "Скачать итоговую ведомость (XLSX)")
    @GetMapping("/{id}/report/final")
    public ResponseEntity<InputStreamResource> getFinalReport(@PathVariable Long id) {
        InputStreamResource resource = seminarService.createFinalReportById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=final_report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Operation(summary = "Импортировать временную ведомость (XLSX)")
    @PostMapping(value = "/import/interim", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> importInterimReport(@RequestPart("file") MultipartFile file) {
        seminarService.importInterimReport(file);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновить пользователей по итоговой ведомости (XLSX)")
    @PostMapping(value = "/import/final", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Collection<UserProfileDto>> updateUsersByReport(
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(seminarService.updateUsersByReport(file));
    }
}

