package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.dto.group.*;
import com.prosvirnin.trainersportal.service.group.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
@Tag(name = "Посещаемость", description = "Управление журналом посещений")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(summary = "Отметить посещение")
    @PostMapping("/mark")
    public ResponseEntity<AttendanceDto> markAttendance(
            @Valid @RequestBody AttendanceMarkRequest request) {
        AttendanceDto result = attendanceService.markAttendance(request);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Получить журнал для клиента")
    @PostMapping("/my")
    public ResponseEntity<Collection<JournalForClientDto>> getJournalForClient(
            @Valid @RequestBody JournalRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(attendanceService.getJournalForClient(userDetails, request));
    }

    @Operation(summary = "Получить журнал для тренера")
    @PostMapping("/trainer")
    public ResponseEntity<Collection<JournalForTrainerDto>> getJournalForTrainer(
            @Valid @RequestBody JournalRequest request) {
        return ResponseEntity.ok(attendanceService.getJournalForTrainer(request));
    } //TODO: отсортировать по поясу.
}
