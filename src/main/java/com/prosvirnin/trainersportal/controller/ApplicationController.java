package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.dto.application.ApplicationDto;
import com.prosvirnin.trainersportal.model.dto.application.CreateApplicationRequest;
import com.prosvirnin.trainersportal.model.dto.application.ReplyApplicationRequest;
import com.prosvirnin.trainersportal.service.application.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
@Tag(name = "Заявки", description = "Работа с заявками")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "Получить все заявки в работе")
    @GetMapping("/in-progress")
    public ResponseEntity<Collection<ApplicationDto>> getAllInProgressApplications() {
        return ResponseEntity.ok(applicationService.findAllInProgressApplications());
    }

    @Operation(summary = "Отклик на заявку по ID")
    @PostMapping("/{id}/reply")
    public ResponseEntity<Void> replyToApplication(
            @PathVariable Long id,
            @RequestBody ReplyApplicationRequest request) {
        applicationService.replyById(id, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Создать новую заявку")
    @PostMapping
    public ResponseEntity<Void> createApplication(
            @RequestBody CreateApplicationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        applicationService.createApplication(userDetails, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
