package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.dto.stats.*;
import com.prosvirnin.trainersportal.service.stats.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@Tag(name = "Статистика", description = "Получение аналитических данных и метрик")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "Получить данные посещаемости по ID пользователя")
    @GetMapping("/attendance/{userId}")
    public ResponseEntity<AttendanceData> getAttendanceData(@PathVariable Long userId) {
        return ResponseEntity.ok(statisticsService.getAttendanceDataByUserId(userId));
    }

    @Operation(summary = "Статистика роста количества учеников")
    @PostMapping("/students/growth")
    public ResponseEntity<ClientsCountStats> getGrowthOfStudents(@RequestBody StatisticsFilterRequest filter) {
        return ResponseEntity.ok(statisticsService.getGrowthRateOfStudentsCount(filter));
    }

    @Operation(summary = "Статистика удержания учеников")
    @PostMapping("/students/retention")
    public ResponseEntity<ClientsRetentionStats> getRetentionStats(@RequestBody StatisticsFilterRequest filter) {
        return ResponseEntity.ok(statisticsService.getClientsRetentionStats(filter));
    }

    @Operation(summary = "Статистика роста количества тренеров")
    @PostMapping("/trainers/growth")
    public ResponseEntity<TrainersGrowthRate> getTrainersGrowth(@RequestBody StatisticsFilterRequest filter) {
        return ResponseEntity.ok(statisticsService.getTrainersGrowthRate(filter));
    }

    @Operation(summary = "Процент активного участия в семинарах")
    @PostMapping("/seminars/participation")
    public ResponseEntity<ActiveParticipationInSeminarsPercentage> getParticipationPercentage(@RequestBody StatisticsFilterRequest filter) {
        return ResponseEntity.ok(statisticsService.getActiveParticipationInSeminarsPercentage(filter));
    }

    @Operation(summary = "Процент сертифицированных участников семинаров")
    @PostMapping("/seminars/certified")
    public ResponseEntity<CertifiedParticipantsInSeminarsPercentage> getCertifiedPercentage(@RequestBody StatisticsFilterRequest filter) {
        return ResponseEntity.ok(statisticsService.getCertifiedParticipantsInSeminarsPercentage(filter));
    }

    @Operation(summary = "Общая выручка по семинарам")
    @PostMapping("/seminars/income")
    public ResponseEntity<SeminarsTotalIncome> getTotalSeminarIncome(@RequestBody StatisticsFilterRequest filter) {
        return ResponseEntity.ok(statisticsService.getSeminarsTotalIncome(filter));
    }
}